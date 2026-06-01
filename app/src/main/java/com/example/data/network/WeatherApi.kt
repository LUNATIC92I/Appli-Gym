package com.example.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class OpenMeteoResponse(
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "current") val current: CurrentWeather?,
    @Json(name = "hourly") val hourly: HourlyForecast?,
    @Json(name = "daily") val daily: DailyForecast?
)

@JsonClass(generateAdapter = true)
data class CurrentWeather(
    @Json(name = "time") val time: String,
    @Json(name = "temperature_2m") val temperature2m: Float,
    @Json(name = "relative_humidity_2m") val relativeHumidity2m: Float,
    @Json(name = "apparent_temperature") val apparentTemperature: Float,
    @Json(name = "precipitation") val precipitation: Float,
    @Json(name = "weather_code") val weatherCode: Int,
    @Json(name = "wind_speed_10m") val windSpeed10m: Float
)

@JsonClass(generateAdapter = true)
data class HourlyForecast(
    @Json(name = "time") val time: List<String>,
    @Json(name = "temperature_2m") val temperature2m: List<Float>,
    @Json(name = "weather_code") val weatherCode: List<Int>
)

@JsonClass(generateAdapter = true)
data class DailyForecast(
    @Json(name = "time") val time: List<String>,
    @Json(name = "temperature_2m_max") val temperature2mMax: List<Float>,
    @Json(name = "temperature_2m_min") val temperature2mMin: List<Float>,
    @Json(name = "weather_code") val weatherCode: List<Int>
)

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,apparent_temperature,precipitation,weather_code,wind_speed_10m",
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weather_code",
        @Query("timezone") timezone: String = "auto"
    ): OpenMeteoResponse
}

object WeatherRetrofitClient {
    private const val BASE_URL = "https://api.open-meteo.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val service: WeatherApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        retrofit.create(WeatherApiService::class.java)
    }
}

data class CityCoordinates(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val translation: String
)

object GeocodingHelper {
    val predefinedCities = listOf(
        CityCoordinates("Paris", 48.8566, 2.3522, "FR", "Paris"),
        CityCoordinates("New York", 40.7128, -74.0060, "US", "New York"),
        CityCoordinates("London", 51.5074, -0.1278, "GB", "Londres"),
        CityCoordinates("Tokyo", 35.6762, 139.6503, "JP", "Tokyo"),
        CityCoordinates("Montreal", 45.5017, -73.5673, "CA", "Montréal"),
        CityCoordinates("Rome", 41.9028, 12.4964, "IT", "Rome"),
        CityCoordinates("Berlin", 52.5200, 13.4050, "DE", "Berlin"),
        CityCoordinates("Sydney", -33.8688, 151.2093, "AU", "Sydney"),
        CityCoordinates("Dakar", 14.7167, -17.4677, "SN", "Dakar"),
        CityCoordinates("Cairo", 30.0444, 31.2357, "EG", "Le Caire"),
        CityCoordinates("Casablanca", 33.5731, -7.5898, "MA", "Casablanca"),
        CityCoordinates("Rio de Janeiro", -22.9068, -43.1729, "BR", "Rio de Janeiro"),
        CityCoordinates("Mumbai", 19.0760, 72.8777, "IN", "Mumbai"),
        CityCoordinates("Los Angeles", 34.0522, -118.2437, "US", "Los Angeles"),
        CityCoordinates("Dubaï", 25.2048, 55.2708, "AE", "Dubaï")
    )

    fun searchCity(query: String): List<CityCoordinates> {
        if (query.isBlank()) return predefinedCities
        return predefinedCities.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.translation.contains(query, ignoreCase = true) ||
            it.country.contains(query, ignoreCase = true)
        }
    }
}

fun getWeatherDescription(code: Int): String {
    return when (code) {
        0 -> "Ciel dégagé"
        1, 2, 3 -> "Partiellement nuageux"
        45, 48 -> "Brouillard"
        51, 53, 55 -> "Bruine légère"
        61, 63, 65 -> "Pluie"
        71, 73, 75 -> "Chute de neige"
        80, 81, 82 -> "Averses de pluie"
        95, 96, 99 -> "Orageux"
        else -> "Météo stable"
    }
}
