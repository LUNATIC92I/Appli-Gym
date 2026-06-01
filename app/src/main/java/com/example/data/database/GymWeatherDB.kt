package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val sex: String = "Male",
    val age: Int = 26,
    val heightCm: Float = 178f,
    val weightKg: Float = 75f,
    val physicalLevel: String = "Intermediate", // Beginner, Intermediate, Advanced
    val goal: String = "Muscle Building", // Weight Loss, Mass Gain, Muscle Building, Cardio, Fitness
    val level: Int = 2,
    val totalXp: Int = 345
)

@Entity(tableName = "workout_logs")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val durationMinutes: Int,
    val caloriesBurned: Int,
    val weightKg: Float,
    val workoutName: String, // e.g., "Mass Heavy Push"
    val weatherCondition: String, // e.g., "Pluvieux", "Ensoleillé"
    val tempCelsius: Float,
    val wasCompleted: Boolean = true
)

@Entity(tableName = "favorite_cities")
data class FavoriteCity(
    @PrimaryKey val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val country: String = "FR"
)

@Dao
interface GymWeatherDao {
    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileDirect(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile)

    // Workout Logs
    @Query("SELECT * FROM workout_logs ORDER BY timestamp DESC")
    fun getAllWorkoutLogs(): Flow<List<WorkoutLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(log: WorkoutLog)

    @Query("DELETE FROM workout_logs")
    suspend fun clearWorkoutLogs()

    // Favorite Cities
    @Query("SELECT * FROM favorite_cities")
    fun getFavoriteCities(): Flow<List<FavoriteCity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCity(city: FavoriteCity)

    @Delete
    suspend fun deleteFavoriteCity(city: FavoriteCity)
}

@Database(entities = [UserProfile::class, WorkoutLog::class, FavoriteCity::class], version = 1, exportSchema = false)
abstract class GymWeatherDatabase : RoomDatabase() {
    abstract fun dao(): GymWeatherDao
}
