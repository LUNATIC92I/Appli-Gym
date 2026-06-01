package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.GymWeatherApplication
import com.example.data.database.FavoriteCity
import com.example.data.database.UserProfile
import com.example.data.database.WorkoutLog
import com.example.data.model.ExerciseLibrary
import com.example.data.model.TrainingProgram
import com.example.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(val response: OpenMeteoResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

data class ChatMessage(val sender: String, val message: String, val timestamp: Long = System.currentTimeMillis())

data class Challenge(val id: String, val title: String, val xpReward: Int, var isCompleted: Boolean = false)

data class NotificationItem(val id: String, val title: String, val body: String, val timeDesc: String, val iconType: String)

class GymWeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = GymWeatherApplication.database.dao()
    private val geminiRepo = GeminiRepository()

    // Selected City Coordinates
    private val _selectedCity = MutableStateFlow(GeocodingHelper.predefinedCities.first())
    val selectedCity: StateFlow<CityCoordinates> = _selectedCity.asStateFlow()

    // Weather state
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    // Profile state
    val userProfile: StateFlow<UserProfile> = dao.getUserProfile()
        .map { it ?: UserProfile() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    // Favorites
    val favoriteCities: StateFlow<List<FavoriteCity>> = dao.getFavoriteCities()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Workouts
    val workoutLogs: StateFlow<List<WorkoutLog>> = dao.getAllWorkoutLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Chat AI
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage("coach", "Bonjour ! Je suis ton coach GymWeather IA. Pose-moi tes questions sur ton entraînement, ta nutrition, ou comment la météo d'aujourd'hui impacte ta forme !")
    ))
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAnalyzingCoach = MutableStateFlow(false)
    val isAnalyzingCoach: StateFlow<Boolean> = _isAnalyzingCoach.asStateFlow()

    // Challenges
    private val _dailyChallenges = MutableStateFlow(listOf(
        Challenge("ch_1", "Boire 2.5L d'eau (Hydratation météo)", 25),
        Challenge("ch_2", "S'échauffer pendant au moins 10 min", 20),
        Challenge("ch_3", "Compléter le programme météo-sport du jour", 50),
        Challenge("ch_4", "Faire une séance d'étirements en fin d'entraînement", 15)
    ))
    val dailyChallenges: StateFlow<List<Challenge>> = _dailyChallenges.asStateFlow()

    // Notifications
    private val _notifications = MutableStateFlow(listOf(
        NotificationItem("n_1", "Conseil Hydratation 💧", "Avec la température actuelle, buvez au moins 500ml à chaque demi-heure.", "Il y a 10 min", "water"),
        NotificationItem("n_2", "Alerte Météo 🌤️", "Le temps est changeant aujourd'hui. Privilégiez l'échauffement intérieur.", "Il y a 1h", "weather"),
        NotificationItem("n_3", "Progression de Niveau ⚡", "Vous êtes proche de monter au niveau supérieur ! Continuez vos efforts.", "Il y a 3h", "level")
    ))
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    // Selected workout
    private val _activeProgram = MutableStateFlow<TrainingProgram?>(null)
    val activeProgram: StateFlow<TrainingProgram?> = _activeProgram.asStateFlow()

    init {
        fetchWeather()
        seedInitialWorkouts()
    }

    private fun seedInitialWorkouts() {
        viewModelScope.launch {
            val count = workoutLogs.value.size
            if (count == 0) {
                // Seed some samples to build a beautiful history graph right away!
                val sampleLogs = listOf(
                    WorkoutLog(durationMinutes = 45, caloriesBurned = 320, weightKg = 75.8f, workoutName = "Cardio Intense", weatherCondition = "Pluvieux", tempCelsius = 12f, timestamp = System.currentTimeMillis() - 4 * 24 * 3600 * 1000),
                    WorkoutLog(durationMinutes = 55, caloriesBurned = 460, weightKg = 75.5f, workoutName = "Pectoraux & Bras", weatherCondition = "Ensoleillé", tempCelsius = 22f, timestamp = System.currentTimeMillis() - 3 * 24 * 3600 * 1000),
                    WorkoutLog(durationMinutes = 50, caloriesBurned = 380, weightKg = 75.2f, workoutName = "Séance Dos Épaules", weatherCondition = "Pluvieux", tempCelsius = 14f, timestamp = System.currentTimeMillis() - 2 * 24 * 3600 * 1000),
                    WorkoutLog(durationMinutes = 65, caloriesBurned = 520, weightKg = 74.9f, workoutName = "Leg Day Complet", weatherCondition = "Ensoleillé", tempCelsius = 26f, timestamp = System.currentTimeMillis() - 1 * 24 * 3600 * 1000)
                )
                sampleLogs.forEach { dao.insertWorkoutLog(it) }
            }
        }
    }

    fun selectCity(city: CityCoordinates) {
        _selectedCity.value = city
        fetchWeather()
    }

    fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            try {
                val city = _selectedCity.value
                val response = withContext(Dispatchers.IO) {
                    WeatherRetrofitClient.service.getForecast(city.latitude, city.longitude)
                }
                _weatherState.value = WeatherUiState.Success(response)
                
                // Re-evaluate current program adaptation according to current temperature
                val currentTemp = response.current?.temperature2m ?: 20f
                val preprog = ExerciseLibrary.generateProgram(
                    goal = userProfile.value.goal,
                    difficulty = userProfile.value.physicalLevel,
                    tempCelsius = currentTemp,
                    precipitation = response.current?.precipitation ?: 0f,
                    windSpeed = response.current?.windSpeed10m ?: 0f
                )
                _activeProgram.value = preprog

                // Trigger intelligent push notification according to weather
                addWeatherNotification(response.current)
            } catch (e: Exception) {
                e.printStackTrace()
                _weatherState.value = WeatherUiState.Error("Erreur de récupération météo : ${e.localizedMessage}")
                // Build a simulated success when offline/network-blocked, so the app remains fully workable
                buildSimulatedWeatherState()
            }
        }
    }

    private fun addWeatherNotification(current: CurrentWeather?) {
        val temp = current?.temperature2m ?: 20f
        val list = _notifications.value.toMutableList()
        val newItem = when {
            temp > 28 -> NotificationItem("n_wt", "Alerte Canicule 🥵", "Température de ${temp}°C ! Réduisez l'effort cardio intense et buvez 1.5L d'eau.", "À l'instant", "water")
            temp < 10 -> NotificationItem("n_wt", "Grand Froid ❄️", "Il fait ${temp}°C. Réalisez un échauffement musculaire de 15 minutes complet.", "À l'instant", "weather")
            current?.precipitation ?: 0f > 1.0f -> NotificationItem("n_wt", "Pluie battante 🌧️", "Pluie forte signalée. Privilégiez nos entraînements indoor !", "À l'instant", "weather")
            else -> NotificationItem("n_wt", "Météo Optimale 🌤️", "Grand beau à ${temp}°C ! Des conditions parfaites pour s'entraîner dur en intérieur.", "À l'instant", "level")
        }
        list.add(0, newItem)
        _notifications.value = list.take(6)
    }

    private fun buildSimulatedWeatherState() {
        val city = _selectedCity.value
        val current = CurrentWeather("2026-06-01T12:00", 18.5f, 65f, 18f, 0f, 1, 12f)
        val hourly = HourlyForecast(
            time = List(24) { "2026-06-01T%02d:00".format(it) },
            temperature2m = List(24) { 15f + it / 2f },
            weatherCode = List(24) { 1 }
        )
        val daily = DailyForecast(
            time = List(7) { "2026-06-0%d".format(it + 1) },
            temperature2mMax = listOf(20f, 22f, 19f, 18f, 21f, 24f, 23f),
            temperature2mMin = listOf(12f, 13f, 11f, 10f, 12f, 14f, 13f),
            weatherCode = listOf(0, 1, 3, 61, 1, 0, 0)
        )
        val response = OpenMeteoResponse(city.latitude, city.longitude, "UTC", current, hourly, daily)
        _weatherState.value = WeatherUiState.Success(response)

        val preprog = ExerciseLibrary.generateProgram(
            goal = userProfile.value.goal,
            difficulty = userProfile.value.physicalLevel,
            tempCelsius = 18.5f,
            precipitation = 0f,
            windSpeed = 12f
        )
        _activeProgram.value = preprog
    }

    fun toggleChallenge(id: String) {
        val updatedList = _dailyChallenges.value.map {
            if (it.id == id) {
                val nextState = !it.isCompleted
                if (nextState) {
                    addXp(it.xpReward)
                } else {
                    addXp(-it.xpReward)
                }
                it.copy(isCompleted = nextState)
            } else it
        }
        _dailyChallenges.value = updatedList
    }

    private fun addXp(amount: Int) {
        viewModelScope.launch {
            val prof = userProfile.value
            var newXp = prof.totalXp + amount
            if (newXp < 0) newXp = 0
            
            // Level formula: level = 1 + XP / 200
            val newLevel = 1 + (newXp / 200)
            
            val updated = prof.copy(totalXp = newXp, level = newLevel)
            dao.insertUserProfile(updated)

            if (newLevel > prof.level) {
                val list = _notifications.value.toMutableList()
                list.add(0, NotificationItem(
                    id = "lvl_${System.currentTimeMillis()}",
                    title = "Niveau Supérieur Gagné ! 🏆",
                    body = "Félicitations ! Vous êtes passé au Niveau $newLevel de GymWeather !",
                    timeDesc = "À l'instant",
                    iconType = "level"
                ))
                _notifications.value = list.take(5)
            }
        }
    }

    fun submitUserProfile(sex: String, age: Int, height: Float, weight: Float, level: String, goal: String) {
        viewModelScope.launch {
            val currentProfile = userProfile.value
            val updated = currentProfile.copy(
                sex = sex,
                age = age,
                heightCm = height,
                weightKg = weight,
                physicalLevel = level,
                goal = goal
            )
            dao.insertUserProfile(updated)

            // Trigger program recalculation with new preferences
            val state = _weatherState.value
            val currentTemp = if (state is WeatherUiState.Success) state.response.current?.temperature2m ?: 20f else 20f
            _activeProgram.value = ExerciseLibrary.generateProgram(
                goal = goal,
                difficulty = level,
                tempCelsius = currentTemp
            )
        }
    }

    fun addFavoriteCity(cityName: String) {
        viewModelScope.launch {
            val predefined = GeocodingHelper.predefinedCities.firstOrNull { it.name.equals(cityName, ignoreCase = true) }
            if (predefined != null) {
                dao.insertFavoriteCity(FavoriteCity(predefined.name, predefined.latitude, predefined.longitude, predefined.country))
            } else {
                dao.insertFavoriteCity(FavoriteCity(cityName, 48.8566, 2.3522, "FR"))
            }
        }
    }

    fun removeFavoriteCity(cityName: String) {
        viewModelScope.launch {
            val fav = favoriteCities.value.firstOrNull { it.cityName == cityName }
            if (fav != null) {
                dao.deleteFavoriteCity(fav)
            }
        }
    }

    fun completeWorkout(durationMinutes: Int, caloriesBurned: Int, workoutName: String) {
        viewModelScope.launch {
            val state = _weatherState.value
            val (weatherDesc, currentTemp) = if (state is WeatherUiState.Success) {
                Pair(getWeatherDescription(state.response.current?.weatherCode ?: 0), state.response.current?.temperature2m ?: 20f)
            } else {
                Pair("Nuageux", 18f)
            }

            val newLog = WorkoutLog(
                durationMinutes = durationMinutes,
                caloriesBurned = caloriesBurned,
                weightKg = userProfile.value.weightKg,
                workoutName = workoutName,
                weatherCondition = weatherDesc,
                tempCelsius = currentTemp
            )
            dao.insertWorkoutLog(newLog)

            // Reward substantial XP for finishing workout
            addXp(150)

            // Update user weight history slightly as mock dynamic adaptation
            val prof = userProfile.value
            // Weight decreases slightly after workout
            val newWeight = (prof.weightKg - 0.1f).coerceAtLeast(40f)
            dao.insertUserProfile(prof.copy(weightKg = newWeight))

            // Auto-complete challenge
            val updated = _dailyChallenges.value.map {
                if (it.id == "ch_3") {
                    if (!it.isCompleted) {
                        addXp(it.xpReward)
                    }
                    it.copy(isCompleted = true)
                } else it
            }
            _dailyChallenges.value = updated
        }
    }

    fun sendChatMessage(userText: String) {
        if (userText.isBlank()) return

        val userMsg = ChatMessage("user", userText)
        _chatMessages.value = _chatMessages.value + userMsg

        _isAnalyzingCoach.value = true
        viewModelScope.launch {
            val profile = userProfile.value
            val weather = _weatherState.value
            val weatherDesc = if (weather is WeatherUiState.Success) {
                "température de ${weather.response.current?.temperature2m}°C, ressentie ${weather.response.current?.apparentTemperature}°C, humidité ${weather.response.current?.relativeHumidity2m}%, météo ${getWeatherDescription(weather.response.current?.weatherCode ?: 0)}"
            } else {
                "météo clémente à 20°C"
            }

            val systemPrompt = """
                Tu es un assistant IA de GymWeather, un coach sportif expert qui intègre l'intelligence météorologique.
                Informations utilisateur :
                - Sexe : ${profile.sex}
                - Âge : ${profile.age} ans
                - Poids : ${profile.weightKg} kg, Taille : ${profile.heightCm} cm (IMC : ${calculateImc(profile.weightKg, profile.heightCm)})
                - Niveau sportif : ${profile.physicalLevel}
                - Objectif : ${profile.goal}
                - Météo actuelle à ${selectedCity.value.name} : $weatherDesc.
                
                Donne des conseils ultra personnalisés, motivants, en français. Propose des ajustements d'exercices à faire en salle si le climat est rude (forte chaleur, pollution, froid intense). Reste court et moderne, utilise des listes à puces de temps en temps !
            """.trimIndent()

            val responseText = geminiRepo.getCoachRecommendation(userText, systemPrompt)
            
            _chatMessages.value = _chatMessages.value + ChatMessage("coach", responseText)
            _isAnalyzingCoach.value = false
        }
    }

    private fun calculateImc(weight: Float, heightCm: Float): String {
        val hM = heightCm / 100f
        if (hM <= 0) return "N/A"
        val imc = weight / (hM * hM)
        return "%.1f".format(imc)
    }

    fun getIMC(): Float {
        val prof = userProfile.value
        val hM = prof.heightCm / 100f
        if (hM <= 0f) return 0f
        return prof.weightKg / (hM * hM)
    }
}
