package com.example

import android.app.Application
import androidx.room.Room
import com.example.data.database.GymWeatherDatabase

class GymWeatherApplication : Application() {
    companion object {
        lateinit var database: GymWeatherDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            GymWeatherDatabase::class.java,
            "gym_weather_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
}
