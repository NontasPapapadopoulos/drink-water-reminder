package com.nondaspap.drinkwaterreminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.nondaspap.drinkwaterreminder.TimeUtil.Companion.convertStringTimeToLocalTime

class ReminderScheduler(
    private val wakeUpTime:String,
    private val sleepTime: String,
    private val waterReminders: Int,
    private val firstSnackReminder: String,
    private val secondSnackReminder:String,
    private val context: Context?
) {


    private var drinkWaterReminders = mutableListOf<String>()
    private var snackReminders = mutableListOf<String>()
    lateinit var sharedPreferences: SharedPreferences


    @RequiresApi(Build.VERSION_CODES.O)
    fun setReminderSchedule() {
        when(waterReminders) {
            1 -> drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(3).toString())
            2 ->  {
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(3).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(sleepTime).minusHours(3).toString())
            }
            3 -> {
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(3).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(6).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(sleepTime).minusHours(3).toString())
            }
            4 -> {
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(3).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(5).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(7).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(sleepTime).minusHours(2).toString())
            }
            5 -> {
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(3).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(5).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(wakeUpTime).plusHours(7).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(sleepTime).minusHours(2).toString())
                drinkWaterReminders.add(convertStringTimeToLocalTime(sleepTime).minusHours(3).toString())
            }
        }

        if (firstSnackReminder.isNotEmpty() &&
            secondSnackReminder.isNotEmpty()) {
            snackReminders.add(firstSnackReminder)
            snackReminders.add(secondSnackReminder)
        }

        saveTimeNotificationsToSharedPreferences()
    }

    private fun saveTimeNotificationsToSharedPreferences() {
        sharedPreferences = context!!.getSharedPreferences("saveNotifications", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putStringSet("waterNotifications", drinkWaterReminders.toSet())
        editor.putStringSet("snackNotifications", snackReminders.toSet())
        editor.commit()
    }

    private fun displaySavedNotifications() {
        sharedPreferences = context!!.getSharedPreferences("saveNotifications", Context.MODE_PRIVATE)
        val waterNotifications = sharedPreferences.getStringSet("waterNotifications", setOf())
        val snackNotifications = sharedPreferences.getStringSet("snackNotifications", setOf())

        for (reminder in waterNotifications!!) {
            Log.d("notifications:","water $reminder")
        }
        for (reminder in snackNotifications!!) {
            Log.d("notifications:","snack $reminder")
        }
    }
}