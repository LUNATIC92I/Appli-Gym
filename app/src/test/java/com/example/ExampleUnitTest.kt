package com.example

import com.example.data.model.ExerciseLibrary
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testImcCalculation() {
        val weight = 75f
        val heightCm = 178f
        val hM = heightCm / 100f
        val imc = weight / (hM * hM)
        assertTrue(imc > 20f && imc < 25f)
    }

    @Test
    fun testWeatherProgramAdaptation() {
        // High temp adaptation checks
        val programHot = ExerciseLibrary.generateProgram("Muscle Building", "Intermédiaire", tempCelsius = 32f)
        assertTrue(programHot.weatherTips.contains("Température élevée"))
        assertEquals(programHot.goal, "Muscle Building")

        // Fresh/cold adaptation checks
        val programCold = ExerciseLibrary.generateProgram("Weight Loss", "Débutant", tempCelsius = 5f)
        assertTrue(programCold.weatherTips.contains("Météo fraîche"))
        assertEquals(programCold.goal, "Weight Loss")
    }
}
