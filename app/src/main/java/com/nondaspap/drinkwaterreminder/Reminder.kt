package com.nondaspap.drinkwaterreminder

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Reminder(val wakeUpTime:String,
                val sleepTime: String,
                val reminders: Int) {


    fun setReminderSchedule() {

//        when(reminders) {
//            1 ->
//            2 ->
//            3 ->
//            4 ->
//            5 ->
//            6 ->
//
//        }

    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun oneReminder() {
//        var wakeUpTime = convert(wakeUpTime)
//        var sleepTime = convert(sleepTime)
//
//        wakeUpTime.plusHours(3)
//
//    }
//
//    private fun twoReminders() {
//        var wakeUpTime = convert(wakeUpTime)
//        wakeUpTime.plusHours(3)
//
//        var sleepTime = convert(sleepTime)
//        sleepTime.minusHours(2)
//
//    }
//
//    private fun threeReminder() {
//
//
//    }
//
//    private fun fourReminders() {
//
//    }
//
//    private fun convert (time: String): LocalTime {
//        val formatter = DateTimeFormatter.ofPattern("hh:mm");
//        return LocalTime.parse(time, formatter);
//    }

}