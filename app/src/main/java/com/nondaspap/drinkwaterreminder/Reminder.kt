package com.nondaspap.drinkwaterreminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Reminder(
    val wakeUpTime:String,
    val sleepTime: String,
    val reminders: Int,
    val context: Context?
) {

    private var remindersList = mutableListOf<String>()
    lateinit var sharedPreferences: SharedPreferences


    @RequiresApi(Build.VERSION_CODES.O)
    fun setReminderSchedule() {

        when(reminders) {
            1 -> remindersList.add(convert(wakeUpTime).plusHours(3).toString())
            2 ->  {
                remindersList.add(convert(wakeUpTime).plusHours(3).toString())
                remindersList.add(convert(sleepTime).minusHours(3).toString())
            }
            3 -> {
                remindersList.add(convert(wakeUpTime).plusHours(3).toString())
                remindersList.add(convert(wakeUpTime).plusHours(6).toString())
                remindersList.add(convert(sleepTime).minusHours(3).toString())
            }
            4 -> {
                remindersList.add(convert(wakeUpTime).plusHours(3).toString())
                remindersList.add(convert(wakeUpTime).plusHours(5).toString())
                remindersList.add(convert(wakeUpTime).plusHours(7).toString())
                remindersList.add(convert(sleepTime).minusHours(2).toString())
            }
            5 -> {
                remindersList.add(convert(wakeUpTime).plusHours(3).toString())
                remindersList.add(convert(wakeUpTime).plusHours(5).toString())
                remindersList.add(convert(wakeUpTime).plusHours(7).toString())
                remindersList.add(convert(sleepTime).minusHours(2).toString())
            }
        }
        saveTimeNotificationsToSharedPreferences()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun convert (time: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
         return LocalTime.parse(time, formatter)
    }

    private fun saveTimeNotificationsToSharedPreferences() {
        sharedPreferences = context!!.getSharedPreferences("saveNotifications", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putStringSet("notifications", remindersList.toSet())
        editor.commit()
    }

}