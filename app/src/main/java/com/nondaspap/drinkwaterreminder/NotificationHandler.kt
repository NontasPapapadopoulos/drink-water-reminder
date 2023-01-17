package com.nondaspap.drinkwaterreminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity



class NotificationHandler(val context: Context?) {

    private var sharedPreferences: SharedPreferences = context!!.getSharedPreferences("saveNotifications", Context.MODE_PRIVATE)

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel("reminderChannel", "reminderChannel", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Description"

            var notificationManager = context!!.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNotifications() {

        val waterReminders = sharedPreferences.getStringSet("waterNotifications", setOf())
        if (waterReminders!!.isNotEmpty()) {
            for (reminder in waterReminders)
                registerNotification(reminder,"Time for Water", "water")
        }

        val snackReminders = sharedPreferences.getStringSet("snackNotifications", setOf())
        if (snackReminders!!.isNotEmpty()) {
            for (reminder in snackReminders)
                registerNotification(reminder, "Time for Snack", "Fruits")

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun registerNotification(reminder: String, title: String, text: String) {
        val intent = Intent(context, ReminderBroadcast::class.java)
        intent.putExtra("title", title)
        intent.putExtra("text",text)

        val pendingIntent: PendingIntent
        if (title == "Time for Snack") {
            pendingIntent = PendingIntent.getBroadcast(context,
                TimeUtil.convertStringTimeToInteger(reminder)+10,
                intent, 0)
        } else {
            pendingIntent = PendingIntent.getBroadcast(context,
                TimeUtil.convertStringTimeToInteger(reminder),
                intent, 0)
        }


        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, TimeUtil.convertStringTimeToInteger(reminder))
        }

        val alarmManager = context!!.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent)

    }

    fun checkIfNotificationsAreSaved() {
        val waterReminders = sharedPreferences.getStringSet("waterNotifications", setOf())
        val snackReminders = sharedPreferences.getStringSet("snackNotifications", setOf())

        for (reminder in waterReminders!!) {
            check(TimeUtil.convertStringTimeToInteger(reminder), "water")
        }

        for (reminder in snackReminders!!) {
            check(TimeUtil.convertStringTimeToInteger(reminder) +10, "snack")
        }
    }

    private fun check(requestCode: Int, type: String) {
        val myIntent = Intent(context, ReminderBroadcast::class.java)

        val isWorking = PendingIntent.getBroadcast(
            context,
            requestCode,
            myIntent,
            PendingIntent.FLAG_NO_CREATE
        ) != null
        if (isWorking) {
            Log.d("alarm $type", "is working")
        } else {
            Log.d("alarm $type", "is not working")
        }
    }

    fun displaySavedNotifications() {
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