package com.nondaspap.drinkwaterreminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var weightEditText: EditText
    private lateinit var workoutMinutesEditText: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var maleRadioButton: RadioButton
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var submitButton: Button
    private lateinit var enableNotificationsSwitchCompat: SwitchCompat
    lateinit var sharedPreferences: SharedPreferences

    private var gender = Gender.MALE



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        submitButton.isClickable = false
        enableNotificationsSwitchCompat.isChecked = false
        attatchListeners()
        enableSubmitButton()
        createNotificationChannel()
        registerNotifications()
    }

    private fun initComponents() {
        this.weightEditText = findViewById(R.id.weightEditText)
        this.workoutMinutesEditText = findViewById(R.id.minutesWorkoutEditText)
        this.maleRadioButton = findViewById(R.id.maleRadioButton)
        this.femaleRadioButton = findViewById(R.id.femaleRadioButton)
        this.submitButton = findViewById(R.id.submitButton)
        this.radioGroup = findViewById(R.id.radioGroup)
        this.enableNotificationsSwitchCompat = findViewById(R.id.enableNotifications)
    }


    private fun attatchListeners() {

        weightEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                    enableSubmitButton()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                               ount: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                }
        })

        workoutMinutesEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                enableSubmitButton()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           ount: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.femaleRadioButton)
                gender = Gender.FEMALE
            })

        submitButton.setOnClickListener {
            submitData()
        }
    }


    private fun enableSubmitButton() {
        submitButton.isClickable = workoutMinutesEditText.text.isNotEmpty() &&
                weightEditText.text.isNotEmpty()
    }

    private fun submitData() {
        var calculator = WaterConsumptionCalculator(weightEditText.text.toString().toInt(),
                                                    workoutMinutesEditText.text.toString().toInt(),
                                                    gender)

        saveData()

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = RemindersFragment()

        val bundle = Bundle()
        bundle.putSerializable("calculator", calculator )
        bundle.putBoolean("enableNotifications", enableNotificationsSwitchCompat.isChecked)

        fragment.arguments = bundle
        fragmentTransaction.add(R.id.frame, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }


    private fun saveData() {
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("weight", weightEditText.text.toString())
        editor.putString("workoutMinutes", workoutMinutesEditText.text.toString())
        editor.putString("selectedGender", gender.toString())
        editor.putBoolean("enableNotifications", enableNotificationsSwitchCompat.isChecked)
        editor.commit()
    }

    private fun getSavedData() {
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)

        weightEditText.text = Editable.Factory.getInstance().newEditable(sharedPreferences.getString("weight", ""))
        workoutMinutesEditText.text = Editable.Factory.getInstance().newEditable(sharedPreferences.getString("workoutMinutes", ""))
        enableNotificationsSwitchCompat.isChecked = sharedPreferences.getBoolean("enableNotifications", false)

    }

    override fun onResume() {
        super.onResume()
        getSavedData()
        //navigateToRemindersIfUserHasEnableNotifications()
    }

    private fun navigateToRemindersIfUserHasEnableNotifications() {
        if (enableNotificationsSwitchCompat.isChecked)
            submitData()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel("reminderChannel", "reminderChannel", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Description"

            var notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }


    private fun registerNotifications() {
        sharedPreferences = getSharedPreferences("saveNotifications", Context.MODE_PRIVATE)

        var waterReminders = sharedPreferences.getStringSet("waterNotifications", setOf())
            if (waterReminders!!.isNotEmpty()) {
                for (reminder in waterReminders) {
                    print(reminder)
                    val intent = Intent(this@MainActivity, ReminderBroadcast::class.java)
                    intent.putExtra("title", "Time to drink water")
                    intent.putExtra("text","aaaaa")
                    val pendingIntent = PendingIntent.getBroadcast(this@MainActivity,
                                                                    reminder.split(":").get(0).toInt(),
                                                                    intent, 0)

                    var alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                    alarmManager.set(AlarmManager.RTC_WAKEUP, 10000 , pendingIntent)
                }
            }


        var snackReminders = sharedPreferences.getStringSet("snackNotifications", setOf())
        if (snackReminders!!.isNotEmpty()) {
            for (reminder in snackReminders) {
                print(reminder)
                val intent = Intent(this@MainActivity, ReminderBroadcast::class.java)
                intent.putExtra("title", "Time for snack")
                intent.putExtra("text","aaaaa")
                val pendingIntent = PendingIntent.getBroadcast(this@MainActivity,
                    reminder.split(":").get(0).toInt(),
                    intent, 0)

                var alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.set(AlarmManager.RTC_WAKEUP, 10000 , pendingIntent)
            }
        }


    }
    }