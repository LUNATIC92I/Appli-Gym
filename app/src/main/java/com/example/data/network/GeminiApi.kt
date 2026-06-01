package com.example.data.network

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GeminiRequest(
    @Json(name = "contents") val contents: List<GeminiContent>,
    @Json(name = "generationConfig") val generationConfig: GeminiGenerationConfig? = null,
    @Json(name = "systemInstruction") val systemInstruction: GeminiContent? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    @Json(name = "parts") val parts: List<GeminiPart>
)

@JsonClass(generateAdapter = true)
data class GeminiPart(
    @Json(name = "text") val text: String
)

@JsonClass(generateAdapter = true)
data class GeminiGenerationConfig(
    @Json(name = "temperature") val temperature: Float? = null,
    @Json(name = "maxOutputTokens") val maxOutputTokens: Int? = null
)

@JsonClass(generateAdapter = true)
data class GeminiResponse(
    @Json(name = "candidates") val candidates: List<GeminiCandidate>?
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    @Json(name = "content") val content: GeminiContent?
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

object GeminiRetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val service: GeminiApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        retrofit.create(GeminiApiService::class.java)
    }
}

class GeminiRepository {
    suspend fun getCoachRecommendation(prompt: String, systemPrompt: String = "Tu es un coach sportif expert en musculation et météo. Réponds en français de manière motivante et concise."): String {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY" || apiKey.contains("placeholder", ignoreCase = true)) {
            return getOfflineFallbackRecommendation(prompt)
        }

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(text = prompt)))),
            generationConfig = GeminiGenerationConfig(temperature = 0.7f, maxOutputTokens = 800),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(text = systemPrompt)))
        )

        return try {
            val response = GeminiRetrofitClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text 
                ?: "Désolé, je ne parviens pas à formuler une réponse. Restez hydraté !"
        } catch (e: Exception) {
            e.printStackTrace()
            // Graceful offline fallback on network or API key error
            getOfflineFallbackRecommendation(prompt)
        }
    }

    private fun getOfflineFallbackRecommendation(prompt: String): String {
        val p = prompt.lowercase()
        return when {
            p.contains("chaud") || p.contains("temperature élevée") || p.contains("sun") || p.contains("soleil") -> {
                "🌡️ *Conseil du Coach (Hors ligne)* : Il fait chaud dehors ! Privilégiez l'entraînement en salle climatisée. Réduisez l'intensité de vos entraînements cardio et buvez au moins 500ml d'eau par heure d'effort. Optez pour des supersets de musculation avec des temps de repos légèrement allongés."
            }
            p.contains("froid") || p.contains("neige") || p.contains("frais") -> {
                "❄️ *Conseil du Coach (Hors ligne)* : Températures fraîches. Un échauffement complet de 15 minutes est obligatoire pour réchauffer vos articulations et prévenir les blessures. Prolongez vos phases cardio et portez des vêtements chauds et respirants."
            }
            p.contains("pluie") || p.contains("pluvieux") || p.contains("rain") -> {
                "🌧️ *Conseil du Coach (Hors ligne)* : Temps pluvieux. C'est le moment idéal pour une séance intense de musculation en salle ou du home-trainer. Concentrez-vous sur vos points faibles musculaires."
            }
            p.contains("pollution") || p.contains("air") || p.contains("qualité de l'air") -> {
                "😷 *Conseil du Coach (Hors ligne)* : La pollution est élevée aujourd'hui ! Évitez impérativement tout exercice ou trajet cardio à l'extérieur. Rendez-vous dans une salle fermée et bien ventilée pour votre séance du jour."
            }
            p.contains("prise de masse") || p.contains("bulking") -> {
                "💪 *Conseil du Coach (Prise de Masse)* : Consommez des repas riches en glucides complexes et protéines. Travaillez sur des séries de 8 à 12 répétitions proches de l'échec sur des exercices polyarticulaires (SQUAT, DÉVELOPPÉ COUCHÉ, SOULEVÉ DE TERRE)."
            }
            p.contains("perte de poids") || p.contains("seche") || p.contains("weight loss") -> {
                "🔥 *Conseil du Coach (Sèche)* : Pour maximiser la dépense calorique, intégrez du HIIT (High-Intensity Interval Training) en fin de séance de musculation et maintenez un léger déficit calorique en limitant les sucres rapides."
            }
            else -> {
                "⚡ *Conseil de GymWeather* : Soyez régulier, écoutez vos sensations et adaptez toujours vos entraînements à votre état de forme et à l'environnement extérieur."
            }
        }
    }
}
