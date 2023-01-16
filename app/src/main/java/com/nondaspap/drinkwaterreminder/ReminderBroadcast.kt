package com.nondaspap.drinkwaterreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var builder = NotificationCompat.Builder(context!!, "reminderChannel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent!!.getStringExtra("title"))
            .setContentText(intent!!.getStringExtra("text"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(200, builder.build())
    }
}