package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.database.UserProfile
import com.example.data.database.WorkoutLog
import com.example.data.model.*
import com.example.data.network.CityCoordinates
import com.example.data.network.GeocodingHelper
import com.example.ui.WeatherUiState
import com.example.data.network.getWeatherDescription
import com.example.ui.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainAppScreen(
    viewModel: GymWeatherViewModel
) {
    var currentTab by remember { mutableStateOf("Dashboard") }
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val activeProgram by viewModel.activeProgram.collectAsStateWithLifecycle()

    var activeWorkoutToRun by remember { mutableStateOf<TrainingProgram?>(null) }

    Scaffold(
        bottomBar = {
            if (activeWorkoutToRun == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars
                ) {
                    val tabs = listOf(
                        Triple("Dashboard", Icons.Filled.Dashboard, Icons.Outlined.Dashboard),
                        Triple("Programmes", Icons.Filled.FitnessCenter, Icons.Outlined.FitnessCenter),
                        Triple("Coach IA", Icons.Filled.Psychology, Icons.Outlined.Psychology),
                        Triple("Suivi", Icons.Filled.Leaderboard, Icons.Outlined.Leaderboard),
                        Triple("Profil", Icons.Filled.Person, Icons.Outlined.Person)
                    )
                    tabs.forEach { (tabName, filledIcon, outlinedIcon) ->
                        val isSelected = currentTab == tabName
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) filledIcon else outlinedIcon,
                                    contentDescription = tabName,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else TextGray
                                )
                            },
                            label = {
                                Text(
                                    text = tabName,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else TextGray
                                )
                            },
                            selected = isSelected,
                            onClick = { currentTab = tabName },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            ),
                            modifier = Modifier.testTag("tab_$tabName")
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            if (activeWorkoutToRun != null) {
                ActiveWorkoutScreen(
                    program = activeWorkoutToRun!!,
                    onDismiss = { activeWorkoutToRun = null },
                    onComplete = { duration, cals ->
                        viewModel.completeWorkout(duration, cals, activeWorkoutToRun!!.name)
                        activeWorkoutToRun = null
                    }
                )
            } else {
                Crossfade(targetState = currentTab, label = "tab_fade") { tab ->
                    when (tab) {
                        "Dashboard" -> DashboardScreen(
                            viewModel = viewModel,
                            weatherState = weatherState,
                            userProfile = userProfile,
                            activeProgram = activeProgram,
                            onStartWorkout = { activeWorkoutToRun = it }
                        )
                        "Programmes" -> ProgramLibraryScreen(
                            viewModel = viewModel,
                            activeProgram = activeProgram,
                            onStartWorkout = { activeWorkoutToRun = it }
                        )
                        "Coach IA" -> CoachIaScreen(
                            viewModel = viewModel,
                            userProfile = userProfile
                        )
                        "Suivi" -> TrackingScreen(
                            viewModel = viewModel,
                            userProfile = userProfile
                        )
                        "Profil" -> ProfileScreen(
                            viewModel = viewModel,
                            userProfile = userProfile
                        )
                    }
                }
            }
        }
    }
}

// ==================== DASHBOARD SCREEN ====================
@Composable
fun DashboardScreen(
    viewModel: GymWeatherViewModel,
    weatherState: WeatherUiState,
    userProfile: UserProfile,
    activeProgram: TrainingProgram?,
    onStartWorkout: (TrainingProgram) -> Unit
) {
    val selectedCity by viewModel.selectedCity.collectAsStateWithLifecycle()
    val challenges by viewModel.dailyChallenges.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    
    var showCitySearch by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        // Top Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, Athlète ! 👋",
                        fontSize = 14.sp,
                        color = TextGray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Niveau ${userProfile.level}",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                // Level badge / XP tracker
                Box(
                    modifier = Modifier
                        .background(AccentCyan.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                        .border(1.dp, AccentCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${userProfile.totalXp} XP",
                        color = AccentCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Weather Dashboard widget
        item {
            when (weatherState) {
                is WeatherUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(DarkSurface, RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AccentCyan)
                    }
                }
                is WeatherUiState.Error -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Oops! impossible de charger la météo", color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.Bold)
                            Text(weatherState.message, color = MaterialTheme.colorScheme.onErrorContainer, fontSize = 12.sp)
                            Button(
                                onClick = { viewModel.fetchWeather() },
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text("Réessayer")
                            }
                        }
                    }
                }
                is WeatherUiState.Success -> {
                    val weather = weatherState.response.current
                    WeatherWidget(
                        city = selectedCity,
                        temp = weather?.temperature2m ?: 21f,
                        apparentTemp = weather?.apparentTemperature ?: 20f,
                        humidity = weather?.relativeHumidity2m ?: 60f,
                        windSpeed = weather?.windSpeed10m ?: 10f,
                        weatherCode = weather?.weatherCode ?: 0,
                        onCityChangeClick = { showCitySearch = true }
                    )
                }
            }
        }

        // Weather Intelligence recommendations
        item {
            activeProgram?.let {
                WeatherIntelligenceBanner(tips = it.weatherTips)
            }
        }

        // Adaptive exercise program of the day
        item {
            Text(
                text = "Ton Programme Adapté ⚡",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        item {
            if (activeProgram != null) {
                AdaptedProgramCard(
                    program = activeProgram,
                    onStartClick = { onStartWorkout(activeProgram) }
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(DarkSurface, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aucun programme sélectionné. Modifiez vos objectifs !", color = TextGray)
                }
            }
        }

        // Gamified challenges checklist
        item {
            Text(
                text = "Défis Quotidiens 🔥",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        items(challenges) { challenge ->
            ChallengeProgressItem(
                challenge = challenge,
                onCheckedChange = { viewModel.toggleChallenge(challenge.id) }
            )
        }

        // In-App Simulated Alerts notifications
        item {
            Text(
                text = "Alertes & Conseils de Récupération 🔔",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        items(notifications) { notif ->
            NotificationCard(item = notif)
        }
    }

    // City selection overlay Dialog
    if (showCitySearch) {
        CitySelectionDialog(
            favorites = viewModel.favoriteCities.collectAsStateWithLifecycle().value,
            onDismiss = { showCitySearch = false },
            onCitySelected = { city ->
                viewModel.selectCity(city)
                showCitySearch = false
            },
            onAddFavorite = { cityName ->
                viewModel.addFavoriteCity(cityName)
            },
            onRemoveFavorite = { cityName ->
                viewModel.removeFavoriteCity(cityName)
            }
        )
    }
}

// ==================== WEATHER WIDGET ====================
@Composable
fun WeatherWidget(
    city: CityCoordinates,
    temp: Float,
    apparentTemp: Float,
    humidity: Float,
    windSpeed: Float,
    weatherCode: Int,
    onCityChangeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkSurface, DarkBg)
                )
            )
            .border(1.dp, BorderGray, RoundedCornerShape(24.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "City",
                        tint = AccentCyan
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${city.name}, ${city.country}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }

                IconButton(
                    onClick = onCityChangeClick,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.08f), CircleShape)
                        .testTag("search_city_button")
                ) {
                    Icon(Icons.Default.DirectionsRun, contentDescription = "Changer", tint = AccentCyan, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${temp.toInt()}°C",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = getWeatherDescription(weatherCode),
                        fontSize = 14.sp,
                        color = AccentCyan,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Render stylized Weather visual Canvas instead of static icons
                WeatherIllustrationCanvas(weatherCode = weatherCode)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = BorderGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherMetricItem(title = "Ressenti", value = "${apparentTemp.toInt()}°", icon = Icons.Outlined.Thermostat, color = OrangeAlert)
                WeatherMetricItem(title = "Humidité", value = "${humidity.toInt()}%", icon = Icons.Outlined.WaterDrop, color = AccentCyan)
                WeatherMetricItem(title = "Vent", value = "${windSpeed.toInt()} km/h", icon = Icons.Outlined.Air, color = AccentBlue)
                WeatherMetricItem(title = "Indice UV", value = "Faible", icon = Icons.Outlined.WbSunny, color = Color.Yellow)
            }
        }
    }
}

@Composable
fun WeatherMetricItem(title: String, value: String, icon: ImageVector, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Text(title, fontSize = 10.sp, color = TextGray)
            Text(value, fontSize = 12.sp, color = TextWhite, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun WeatherIllustrationCanvas(weatherCode: Int) {
    Canvas(modifier = Modifier.size(64.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        when (weatherCode) {
            0 -> { // Sun
                drawCircle(color = Color.Yellow, radius = 22f, center = center)
                for (i in 0..7) {
                    val angle = (i * 45) * Math.PI / 180f
                    val startX = (center.x + Math.cos(angle) * 28f).toFloat()
                    val startY = (center.y + Math.sin(angle) * 28f).toFloat()
                    val endX = (center.x + Math.cos(angle) * 38f).toFloat()
                    val endY = (center.y + Math.sin(angle) * 38f).toFloat()
                    drawLine(color = Color.Yellow, start = Offset(startX, startY), end = Offset(endX, endY), strokeWidth = 5f)
                }
            }
            61, 63, 65, 80, 81, 82 -> { // Rain / Showers
                // Cloud outline
                drawCircle(color = Color.LightGray, radius = 18f, center = Offset(center.x - 10f, center.y - 5f))
                drawCircle(color = Color.White, radius = 22f, center = Offset(center.x + 8f, center.y - 10f))
                drawRect(color = Color.White, topLeft = Offset(center.x - 14f, center.y - 12f), size = androidx.compose.ui.geometry.Size(32f, 22f))
                // Rain drops
                drawLine(color = AccentCyan, start = Offset(center.x - 10f, center.y + 15f), end = Offset(center.x - 18f, center.y + 28f), strokeWidth = 4f)
                drawLine(color = AccentCyan, start = Offset(center.x, center.y + 15f), end = Offset(center.x - 8f, center.y + 28f), strokeWidth = 4f)
                drawLine(color = AccentCyan, start = Offset(center.x + 10f, center.y + 15f), end = Offset(center.x + 2f, center.y + 28f), strokeWidth = 4f)
            }
            else -> { // Cloud template
                drawCircle(color = Color.DarkGray, radius = 18f, center = Offset(center.x - 12f, center.y - 2f))
                drawCircle(color = Color.LightGray, radius = 20f, center = Offset(center.x + 10f, center.y - 8f))
                drawCircle(color = Color.Gray, radius = 24f, center = Offset(center.x, center.y - 4f))
            }
        }
    }
}

// ==================== WEATHER ADVISORY BANNER ====================
@Composable
fun WeatherIntelligenceBanner(tips: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AccentBlue.copy(alpha = 0.15f))
            .border(1.dp, AccentBlue.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Advisory",
                tint = AccentCyan,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = tips,
                color = TextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 18.sp
            )
        }
    }
}

// ==================== RECOMENDED ROUTINE CARD ====================
@Composable
fun AdaptedProgramCard(
    program: TrainingProgram,
    onStartClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.dp, BorderGray)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = program.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "Objectif : ${program.goal}",
                        fontSize = 12.sp,
                        color = AccentCyan,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .background(AccentBlue.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(program.difficulty, fontSize = 11.sp, color = AccentBlue, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Timer, contentDescription = "Durée", tint = TextGray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${program.estDurationMinutes} min", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Text(text = "Durée estimée", color = TextGray, fontSize = 11.sp)
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocalFireDepartment, contentDescription = "Calories", tint = OrangeAlert, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${program.estCalories} kcal", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Text(text = "Calories brulées", color = TextGray, fontSize = 11.sp)
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.List, contentDescription = "Exercices", tint = AccentCyan, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${program.exercises.size} Ex.", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Text(text = "Éléments", color = TextGray, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = BorderGray)
            Spacer(modifier = Modifier.height(14.dp))

            // Display a quick summary of the exercises in this program
            Text("Aperçu des mouvements :", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(program.exercises) { pEx ->
                    Box(
                        modifier = Modifier
                            .background(DarkCard, RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(pEx.exercise.name, color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(containerColor = AccentCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("start_workout_button")
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Démarrer")
                Spacer(modifier = Modifier.width(8.dp))
                Text("DÉMARRER LA SÉANCE", fontWeight = FontWeight.Black, fontSize = 13.sp)
            }
        }
    }
}

// ==================== DAILY CHALLENGE ITEM ====================
@Composable
fun ChallengeProgressItem(
    challenge: Challenge,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkSurface)
            .border(1.dp, if (challenge.isCompleted) ActiveGreen.copy(alpha = 0.5f) else BorderGray, RoundedCornerShape(16.dp))
            .clickable { onCheckedChange(!challenge.isCompleted) }
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (challenge.isCompleted) ActiveGreen else BorderGray),
                contentAlignment = Alignment.Center
            ) {
                if (challenge.isCompleted) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Fait", tint = Color.Black, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = challenge.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (challenge.isCompleted) TextGray else TextWhite
            )
        }

        Box(
            modifier = Modifier
                .background(if (challenge.isCompleted) ActiveGreen.copy(alpha = 0.15f) else AccentCyan.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = "+${challenge.xpReward} XP",
                fontSize = 11.sp,
                color = if (challenge.isCompleted) ActiveGreen else AccentCyan,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== ALERT NOTIFICATION CARD ====================
@Composable
fun NotificationCard(item: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkSurface)
            .border(1.dp, BorderGray, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(AccentBlue.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (item.iconType) {
                    "water" -> Icons.Default.WaterDrop
                    "weather" -> Icons.Default.WbSunny
                    else -> Icons.Default.Star
                },
                contentDescription = "Notif Icon",
                tint = AccentCyan,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                Text(text = item.timeDesc, fontSize = 10.sp, color = TextGray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.body, fontSize = 12.sp, color = TextGray, lineHeight = 16.sp)
        }
    }
}

// ==================== CITY SELECTION DIALOG ====================
@Composable
fun CitySelectionDialog(
    favorites: List<com.example.data.database.FavoriteCity>,
    onDismiss: () -> Unit,
    onCitySelected: (CityCoordinates) -> Unit,
    onAddFavorite: (String) -> Unit,
    onRemoveFavorite: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val matchingCities = GeocodingHelper.searchCity(query)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Sélectionnez votre Ville", fontWeight = FontWeight.Bold, color = TextWhite) },
        containerColor = DarkSurface,
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Rechercher Paris, Dakar, New York...", color = TextGray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentCyan,
                        unfocusedBorderColor = BorderGray,
                        focusedContainerColor = DarkBg,
                        unfocusedContainerColor = DarkBg
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Predefined list title
                Text("Villes Disponibles (${matchingCities.size}) :", fontSize = 12.sp, color = TextGray, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.height(200.dp)) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(matchingCities) { city ->
                            val isFav = favorites.any { it.cityName == city.name }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(DarkCard)
                                    .clickable { onCitySelected(city) }
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(city.translation, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(city.country, color = TextGray, fontSize = 11.sp)
                                }

                                IconButton(
                                    onClick = {
                                        if (isFav) onRemoveFavorite(city.name) else onAddFavorite(city.name)
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                        contentDescription = "Favori",
                                        tint = if (isFav) Color.Red else TextGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer", color = AccentCyan)
            }
        }
    )
}

// ==================== WORKOUT PROGRAM LIBRARY SCREEN ====================
@Composable
fun ProgramLibraryScreen(
    viewModel: GymWeatherViewModel,
    activeProgram: TrainingProgram?,
    onStartWorkout: (TrainingProgram) -> Unit
) {
    var selectedMuscleGroup by remember { mutableStateOf("Tous") }
    var expandedExercise by remember { mutableStateOf<Exercise?>(null) }

    val muscleGroups = listOf("Tous", "Pectoraux", "Dos", "Épaules", "Biceps", "Triceps", "Abdominaux", "Jambes", "Cardio")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Text(
                text = "Entraînements & Découvertes 🏋️‍♀️",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextWhite
            )
        }

        // Adapted current routine block
        activeProgram?.let {
            item {
                Text("Recommandation Météo Active :", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                AdaptedProgramCard(program = it, onStartClick = { onStartWorkout(it) })
            }
        }

        item {
            Divider(color = BorderGray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bibliothèque d'Exercices",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        // Muscle Selector Horizontal chip bar
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(muscleGroups) { muscle ->
                    val isSelected = selectedMuscleGroup == muscle
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AccentCyan else DarkSurface)
                            .border(1.dp, if (isSelected) AccentCyan else BorderGray, RoundedCornerShape(12.dp))
                            .clickable { selectedMuscleGroup = muscle }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("muscle_$muscle")
                    ) {
                        Text(
                            text = muscle,
                            color = if (isSelected) Color.Black else TextGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Subtitled exercises list
        val displayedExercises = if (selectedMuscleGroup == "Tous") {
            ExerciseLibrary.list
        } else {
            ExerciseLibrary.getExercisesByMuscle(selectedMuscleGroup)
        }

        items(displayedExercises) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurface)
                    .border(1.dp, BorderGray, RoundedCornerShape(16.dp))
                    .clickable { expandedExercise = item }
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(AccentCyan.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = item.name, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Cible principale : ${item.primaryMuscle}", color = TextGray, fontSize = 11.sp)
                    }
                }

                Box(
                    modifier = Modifier
                        .background(
                            when(item.difficulty) {
                                "Débutant" -> ActiveGreen.copy(alpha = 0.15f)
                                "Intermédiaire" -> OrangeAlert.copy(alpha = 0.15f)
                                else -> Color.Red.copy(alpha = 0.15f)
                            },
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        item.difficulty,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when(item.difficulty) {
                            "Débutant" -> ActiveGreen
                            "Intermédiaire" -> OrangeAlert
                            else -> Color.Red
                        }
                    )
                }
            }
        }
    }

    // Exercise details dialog overlay
    if (expandedExercise != null) {
        ExerciseDetailsDialog(
            exercise = expandedExercise!!,
            onDismiss = { expandedExercise = null }
        )
    }
}

@Composable
fun ExerciseDetailsDialog(
    exercise: Exercise,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(exercise.name, color = TextWhite, fontWeight = FontWeight.ExtraBold) },
        containerColor = DarkSurface,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Interactive miniature visual Canvas illustration of the motion
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DarkBg),
                    contentAlignment = Alignment.Center
                ) {
                    ExerciseInstructionCanvas(animationType = exercise.animationType)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text("Description :", color = AccentCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(exercise.description, color = TextWhite, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp), lineHeight = 16.sp)

                Spacer(modifier = Modifier.height(14.dp))

                Text("Muscles Secondaires :", color = AccentCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(exercise.secondaryMuscles.joinToString(", "), color = TextGray, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(14.dp))

                Text("Guide d'exécution étape par étape :", color = AccentCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(6.dp))
                exercise.guideSteps.forEachIndexed { i, step ->
                    Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.Top) {
                        Text("${i+1}.", color = AccentCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.width(20.dp))
                        Text(step, color = TextWhite, fontSize = 12.sp, modifier = Modifier.weight(1f), lineHeight = 16.sp)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Compris !", color = AccentCyan)
            }
        }
    )
}

// Illustrated dynamic exercise representations on canvas using math and simple figures
@Composable
fun ExerciseInstructionCanvas(animationType: String) {
    var phase by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = true) {
        while(true) {
            phase = (phase + 0.05f) % (2f * Math.PI.toFloat())
            delay(16)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val offsetMovement = Math.sin(phase.toDouble()).toFloat() * 15f

        when (animationType) {
            "bench_press" -> {
                // Bench
                drawLine(color = Color.DarkGray, start = Offset(center.x - 70f, center.y + 30f), end = Offset(center.x + 70f, center.y + 30f), strokeWidth = 8f)
                drawLine(color = Color.DarkGray, start = Offset(center.x - 50f, center.y + 30f), end = Offset(center.x - 50f, center.y + 70f), strokeWidth = 8f)
                drawLine(color = Color.DarkGray, start = Offset(center.x + 50f, center.y + 30f), end = Offset(center.x + 50f, center.y + 70f), strokeWidth = 8f)

                // Body
                drawCircle(color = Color.White, radius = 10f, center = Offset(center.x - 45f, center.y + 15f)) // Head
                drawLine(color = Color.White, start = Offset(center.x - 35f, center.y + 20f), end = Offset(center.x + 35f, center.y + 20f), strokeWidth = 14f) // Chest

                // Barbell moving
                val barY = center.y - 10f + offsetMovement
                drawLine(color = AccentCyan, start = Offset(center.x - 80f, barY), end = Offset(center.x + 80f, barY), strokeWidth = 6f) // bar
                drawRect(color = AccentBlue, topLeft = Offset(center.x - 85f, barY - 14f), size = androidx.compose.ui.geometry.Size(10f, 28f)) // plates left
                drawRect(color = AccentBlue, topLeft = Offset(center.x + 75f, barY - 14f), size = androidx.compose.ui.geometry.Size(10f, 28f)) // plates right
            }
            "squat" -> {
                // Ground
                drawLine(color = Color.DarkGray, start = Offset(center.x - 60f, center.y + 60f), end = Offset(center.x + 60f, center.y + 60f), strokeWidth = 4f)

                // Stick person performing squats
                val squatFactor = (Math.sin(phase.toDouble()).toFloat() + 1f) / 2f // 0 to 1
                val hipY = center.y + 10f + squatFactor * 25f
                val headY = center.y - 35f + squatFactor * 25f

                drawCircle(color = Color.White, radius = 12f, center = Offset(center.x, headY)) // Head
                drawLine(color = Color.White, start = Offset(center.x, headY + 12f), end = Offset(center.x, hipY), strokeWidth = 8f) // Torso
                drawLine(color = Color.White, start = Offset(center.x, hipY), end = Offset(center.x - 20f, center.y + 60f), strokeWidth = 8f) // Legs
            }
            "pushup" -> {
                // Ground
                drawLine(color = Color.DarkGray, start = Offset(center.x - 80f, center.y + 40f), end = Offset(center.x + 80f, center.y + 40f), strokeWidth = 4f)

                // Angled body
                val pushupHeight = 15f + offsetMovement
                drawCircle(color = Color.White, radius = 10f, center = Offset(center.x - 45f, center.y + 10f - pushupHeight)) // Head
                drawLine(color = Color.White, start = Offset(center.x - 35f, center.y + 13f - pushupHeight), end = Offset(center.x + 45f, center.y + 35f), strokeWidth = 10f) // Body
                drawLine(color = AccentCyan, start = Offset(center.x - 25f, center.y + 20f - pushupHeight), end = Offset(center.x - 25f, center.y + 40f), strokeWidth = 6f) // Arms
            }
            else -> {
                // General active weights indicator
                drawCircle(color = AccentCyan, radius = 25f + offsetMovement / 3f, center = center, style = Stroke(width = 4f))
                drawCircle(color = AccentBlue, radius = 12f, center = center)
            }
        }
    }
}

// ==================== ACTIVE WORKOUT SCREEN ====================
@Composable
fun ActiveWorkoutScreen(
    program: TrainingProgram,
    onDismiss: () -> Unit,
    onComplete: (durationSec: Int, calories: Int) -> Unit
) {
    var currentExerciseIndex by remember { mutableStateOf(0) }
    val currentProgExercise = program.exercises.getOrNull(currentExerciseIndex)

    var workoutSeconds by remember { mutableStateOf(0) }
    var activeRepsChecked by remember { mutableStateOf(0) } // sets completed tracker

    // Rest timer state
    var isResting by remember { mutableStateOf(false) }
    var restTimerSeconds by remember { mutableStateOf(45) }

    LaunchedEffect(key1 = isResting) {
        if (!isResting) return@LaunchedEffect
        while (restTimerSeconds > 0) {
            delay(1000)
            restTimerSeconds--
        }
        isResting = false
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            workoutSeconds++
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Upper navigation bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Annuler", tint = Color.White)
                }

                Text(
                    text = "DURÉE : ${workoutSeconds / 60}m ${workoutSeconds % 60}s",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AccentCyan
                )

                Text(
                    text = "${currentExerciseIndex + 1}/${program.exercises.size} Ex.",
                    fontSize = 14.sp,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold
                )
            }

            if (currentProgExercise != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "EXERCICE CONCOURANT",
                        fontSize = 11.sp,
                        color = AccentBlue,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = currentProgExercise.exercise.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dynamic illustrated animation
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(160.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(DarkSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        ExerciseInstructionCanvas(animationType = currentProgExercise.exercise.animationType)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isResting) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "TEMPS DE REPOS",
                                color = ActiveGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                            Text(
                                text = "${restTimerSeconds}s",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Black,
                                color = TextWhite
                            )
                            TextButton(onClick = { isResting = false }) {
                                Text("Passer le repos (Skip)", color = AccentCyan)
                            }
                        }
                    } else {
                        // Display Sets and Reps parameters
                        Row(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${currentProgExercise.sets}", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
                                Text(text = "Séries", color = TextGray, fontSize = 11.sp)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = currentProgExercise.reps, fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
                                Text(text = "Répétitions", color = TextGray, fontSize = 11.sp)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${currentProgExercise.restSeconds}s", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
                                Text(text = "Repos", color = TextGray, fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Progress circles for series
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            for (s in 1..currentProgExercise.sets) {
                                val isDone = s <= activeRepsChecked
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(if (isDone) ActiveGreen else DarkCard)
                                        .border(2.dp, if (isDone) ActiveGreen else BorderGray, CircleShape)
                                        .clickable {
                                            activeRepsChecked = if (isDone) s - 1 else s
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$s",
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDone) Color.Black else TextWhite
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Cliquez sur les ronds pour valider vos séries",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                    }
                }
            } else {
                Text("Error", color = Color.White)
            }

            // Lower Action controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (!isResting && activeRepsChecked >= (currentProgExercise?.sets ?: 0) && currentProgExercise != null) {
                    Button(
                        onClick = {
                            if (currentExerciseIndex < program.exercises.size - 1) {
                                isResting = true
                                restTimerSeconds = currentProgExercise.restSeconds
                                currentExerciseIndex++
                                activeRepsChecked = 0
                            } else {
                                // Workout complete !
                                onComplete(workoutSeconds / 60, program.estCalories)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ActiveGreen, contentColor = Color.Black),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = if (currentExerciseIndex == program.exercises.size - 1) "ENREGISTRER & CRÉER" else "REPOS APRES EXERCICE",
                            fontWeight = FontWeight.Black
                        )
                    }
                } else if (!isResting) {
                    Button(
                        onClick = {
                            if (currentExerciseIndex < program.exercises.size - 1) {
                                currentExerciseIndex++
                                activeRepsChecked = 0
                            } else {
                                onComplete(workoutSeconds / 60, program.estCalories)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentCyan, contentColor = Color.Black),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = if (currentExerciseIndex == program.exercises.size - 1) "TERMINER L'ENTRAINEMENT" else "EXERCICE SUIVANT",
                            fontWeight = FontWeight.Black
                        )
                    }
                } else {
                    Button(
                        onClick = { isResting = false },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("IGNORER LE REPOS", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ==================== AI COACH ASSISTANT SCREEN ====================
@Composable
fun CoachIaScreen(
    viewModel: GymWeatherViewModel,
    userProfile: UserProfile
) {
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val isAnalyzing by viewModel.isAnalyzingCoach.collectAsStateWithLifecycle()
    
    var textInput by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            lazyListState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Coach info banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DarkSurface)
                .border(1.dp, BorderGray, RoundedCornerShape(16.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(AccentCyan.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Coach GymWeather IA", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 15.sp)
                Text(text = "Conseils sportifs & physiologie météo", color = ActiveGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chat conversation messages scroll
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(chatMessages) { chat ->
                    val isCoach = chat.sender == "coach"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isCoach) Arrangement.Start else Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.82f)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isCoach) 2.dp else 16.dp,
                                        bottomEnd = if (isCoach) 16.dp else 2.dp
                                    )
                                )
                                .background(if (isCoach) DarkSurface else AccentBlue)
                                .border(1.dp, if (isCoach) BorderGray else AccentBlue, RoundedCornerShape(16.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = chat.message,
                                fontSize = 13.sp,
                                color = TextWhite,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                if (isAnalyzing) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = AccentCyan, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Le coach réfléchit...", color = TextGray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Text input field
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text("Posez votre question (ex: Quelle alimentation pour sécher ?)...", color = TextGray, fontSize = 12.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentCyan,
                    unfocusedBorderColor = BorderGray,
                    focusedContainerColor = DarkSurface,
                    unfocusedContainerColor = DarkSurface
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_coach_input"),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        viewModel.sendChatMessage(textInput)
                        textInput = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(AccentCyan, RoundedCornerShape(12.dp))
                    .testTag("send_ai_message")
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Envoyer", tint = Color.Black)
            }
        }
    }
}

// ==================== PERFORMANCE TRACK SCREEN ====================
@Composable
fun TrackingScreen(
    viewModel: GymWeatherViewModel,
    userProfile: UserProfile
) {
    val workoutLogs by viewModel.workoutLogs.collectAsStateWithLifecycle()
    
    // Compute metrics
    val cumulativeTime = workoutLogs.sumOf { it.durationMinutes }
    val cumulativeCalories = workoutLogs.sumOf { it.caloriesBurned }
    val currentImc = viewModel.getIMC()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Text(
                text = "Performances & Progression 📊",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextWhite
            )
        }

        // Row of quick statistics cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    title = "Temps total",
                    value = "${cumulativeTime} min",
                    icon = Icons.Default.QueryBuilder,
                    color = AccentCyan,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Calories",
                    value = "${cumulativeCalories} kcal",
                    icon = Icons.Default.LocalFireDepartment,
                    color = OrangeAlert,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "IMC Actuel",
                    value = "%.1f".format(currentImc),
                    icon = Icons.Default.ShowChart,
                    color = ActiveGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Custom Weight evolution graph
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, BorderGray)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Évolution de votre Poids (kg)",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Données synchronisées automatiquement",
                        color = TextGray,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    WeightEvolutionCanvasChart(workoutLogs = workoutLogs, targetWeightVal = userProfile.weightKg)
                }
            }
        }

        // Custom burned calories bar graph
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, BorderGray)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Dépense Calorique par Séance",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Dernières sessions",
                        color = TextGray,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    CaloriesBurnedBarCanvasChart(workoutLogs = workoutLogs)
                }
            }
        }

        // Historical list of past workouts
        item {
            Text(
                text = "Historique d'Entraînements",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        if (workoutLogs.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aucun historique pour le moment. À vos poids !", color = TextGray)
                }
            }
        }

        items(workoutLogs) { log ->
            val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
            val dateStr = sdf.format(Date(log.timestamp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurface)
                    .border(1.dp, BorderGray, RoundedCornerShape(16.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = log.workoutName, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = "$dateStr • Météo: ${log.weatherCondition} (${log.tempCelsius.toInt()}°C)", color = TextGray, fontSize = 11.sp)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "${log.caloriesBurned} kcal", color = OrangeAlert, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                    Text(text = "${log.durationMinutes} min", color = AccentCyan, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderGray)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(24.dp))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextWhite)
            Text(text = title, fontSize = 10.sp, color = TextGray)
        }
    }
}

// Dynamic interactive Weight graph drawn on Canvas
@Composable
fun WeightEvolutionCanvasChart(workoutLogs: List<WorkoutLog>, targetWeightVal: Float) {
    val points = workoutLogs.take(5).reversed()
    
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
    ) {
        if (points.isEmpty()) {
            // Draw baseline if empty history
            drawLine(color = BorderGray, start = Offset(0f, size.height / 2f), end = Offset(size.width, size.height / 2f), strokeWidth = 2f)
            return@Canvas
        }

        val maxWeight = points.maxOf { it.weightKg }.coerceAtLeast(80f) + 2f
        val minWeight = points.minOf { it.weightKg }.coerceAtMost(60f) - 2f
        val weightRange = maxWeight - minWeight

        val stepX = size.width / (points.size + 1)
        val path = Path()

        val renderedPoints = mutableListOf<Offset>()

        points.forEachIndexed { index, log ->
            val x = (index + 1) * stepX
            val ratio = (log.weightKg - minWeight) / weightRange
            val y = size.height - (ratio * size.height * 0.7f) - size.height * 0.15f
            
            val offset = Offset(x, y)
            renderedPoints.add(offset)

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        // Draw line curve
        drawPath(path = path, color = AccentCyan, style = Stroke(width = 6f, cap = StrokeCap.Round, join = StrokeJoin.Round))

        // Draw dots & weight metrics
        renderedPoints.forEachIndexed { i, offset ->
            drawCircle(color = AccentBlue, radius = 6f, center = offset)
            drawCircle(color = TextWhite, radius = 10f, center = offset, style = Stroke(width = 3f))
            
            // Draw weight value text (drawn with canvas background indicator manually or we can keep dots clear)
        }
        
        // Draw baseline
        drawLine(color = BorderGray, start = Offset(0f, size.height - 10f), end = Offset(size.width, size.height - 10f), strokeWidth = 3f)
    }
}

// Dynamic interactive Calories bar graph drawn on Canvas
@Composable
fun CaloriesBurnedBarCanvasChart(workoutLogs: List<WorkoutLog>) {
    val logs = workoutLogs.take(6).reversed()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
    ) {
        if (logs.isEmpty()) {
            return@Canvas
        }

        val maxCal = logs.maxOf { it.caloriesBurned }.coerceAtLeast(400)
        val stepX = size.width / (logs.size + 1)
        val barWidth = 26f

        logs.forEachIndexed { i, log ->
            val x = (i + 1) * stepX
            val ratio = log.caloriesBurned.toFloat() / maxCal.toFloat()
            val barHeight = ratio * size.height * 0.8f
            
            // Draw rounded bar
            val rect = androidx.compose.ui.geometry.Rect(
                offset = Offset(x - barWidth / 2f, size.height - barHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
            )
            drawRect(
                color = OrangeAlert,
                topLeft = rect.topLeft,
                size = rect.size
            )
        }

        // Draw baseline
        drawLine(color = BorderGray, start = Offset(0f, size.height - 2f), end = Offset(size.width, size.height - 2f), strokeWidth = 2f)
    }
}

// ==================== USER PROFILE SCREEN ====================
@Composable
fun ProfileScreen(
    viewModel: GymWeatherViewModel,
    userProfile: UserProfile
) {
    val favorites by viewModel.favoriteCities.collectAsStateWithLifecycle()

    var sex by remember(userProfile.sex) { mutableStateOf(userProfile.sex) }
    var age by remember(userProfile.age) { mutableStateOf(userProfile.age.toString()) }
    var height by remember(userProfile.heightCm) { mutableStateOf(userProfile.heightCm.toString()) }
    var weight by remember(userProfile.weightKg) { mutableStateOf(userProfile.weightKg.toString()) }
    var physicalLevel by remember(userProfile.physicalLevel) { mutableStateOf(userProfile.physicalLevel) }
    var goal by remember(userProfile.goal) { mutableStateOf(userProfile.goal) }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Text(
                text = "Votre Profil Athlète 👤",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextWhite
            )
        }

        // Interactive profile forms
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, BorderGray)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Caractéristiques de départ :", color = AccentCyan, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    
                    // Sex Segmented Selector
                    Column {
                        Text("Sexe :", color = TextGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(top = 6.dp)) {
                            listOf("Male", "Female", "Non-Binary").forEach { gender ->
                                val isSelected = sex == gender
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) AccentCyan else DarkBg)
                                        .clickable { sex = gender }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(gender, color = if (isSelected) Color.Black else TextGray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                    // Numeric Fields (Age, Height, Weight)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = age,
                            onValueChange = { age = it },
                            label = { Text("Âge", color = TextGray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = AccentCyan, unfocusedBorderColor = BorderGray),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            label = { Text("Taille (cm)", color = TextGray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = AccentCyan, unfocusedBorderColor = BorderGray),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Poids (kg)", color = TextGray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = AccentCyan, unfocusedBorderColor = BorderGray),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Physical Athletic Level dropdown simulation
                    Column {
                        Text("Niveau Sportif :", color = TextGray, fontSize = 11.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 6.dp)) {
                            listOf("Débutant", "Intermédiaire", "Avancé").forEach { lev ->
                                val isSelected = physicalLevel == lev
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) AccentBlue else DarkBg)
                                        .clickable { physicalLevel = lev }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(lev, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    // Goals selector
                    Column {
                        Text("Objectif de l'entraînement :", color = TextGray, fontSize = 11.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 6.dp)) {
                            listOf("Weight Loss", "Mass Gain", "Muscle Building", "Cardio").forEach { gl ->
                                val isSelected = goal == gl
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) AccentCyan else DarkBg)
                                        .clickable { goal = gl }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(gl, color = if (isSelected) Color.Black else TextGray, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val ageVal = age.toIntOrNull() ?: 25
                            val heightVal = height.toFloatOrNull() ?: 175f
                            val weightVal = weight.toFloatOrNull() ?: 70f
                            viewModel.submitUserProfile(sex, ageVal, heightVal, weightVal, physicalLevel, goal)
                            Toast.makeText(context, "Profil enregistré !", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentCyan, contentColor = Color.Black),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_profile_button")
                    ) {
                        Text("ENREGISTRER MES MODIFICATIONS", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Favorite cities List
        item {
            Text(
                text = "Mes Villes Favorites ❤️",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        if (favorites.isEmpty()) {
            item {
                Text("Aucune ville favorite. Ajoutez-les dans le sélecteur !", color = TextGray, fontSize = 12.sp)
            }
        }

        items(favorites) { city ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(DarkSurface)
                    .border(1.dp, BorderGray, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(city.cityName, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Latitude: ${city.latitude} • Longitude: ${city.longitude}", color = TextGray, fontSize = 11.sp)
                }

                IconButton(
                    onClick = { viewModel.removeFavoriteCity(city.cityName) }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
                }
            }
        }
    }
}
