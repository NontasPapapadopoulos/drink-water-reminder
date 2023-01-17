package com.nondaspap.drinkwaterreminder


import java.io.Serializable
import java.math.RoundingMode
import java.text.DecimalFormat

class WaterConsumptionCalculator(val weight: Int,
                                 val minutesWorkout: Int) : Serializable {

    private var KG_TO_POUND =  2.20462
    private var OUNCE_TO_KG = 0.0283495

    public fun calculateWater() : String {
        val litersWithoutExercise = convertKgToPounds() * 0.67
        val total = litersWithoutExercise + getExtraWaterConsumptionForWorkingOut()
        return convertOunceToKg(total)
    }

    private fun getExtraWaterConsumptionForWorkingOut(): Double {
        return minutesWorkout / 30.0
    }

    private fun convertKgToPounds(): Double {
        return weight * KG_TO_POUND
    }

    private fun convertOunceToKg(total: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(total * OUNCE_TO_KG)

    }
}