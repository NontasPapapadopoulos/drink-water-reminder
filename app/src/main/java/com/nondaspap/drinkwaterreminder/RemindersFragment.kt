package com.nondaspap.drinkwaterreminder

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi


class RemindersFragment : Fragment() {

    private lateinit var calculateAgainButton: Button
    private lateinit var saveNotificationsButton: Button
    private lateinit var resultsTextView: TextView
    private lateinit var sleepTimeSpinner: Spinner
    private lateinit var wakeUpTimeSpinner: Spinner
    private lateinit var remindersSpinner: Spinner
    private lateinit var timeWakeUpTextView: TextView
    private lateinit var timeSleepTextView: TextView
    private lateinit var remidersTextView: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var noRadioButton: RadioButton
    private lateinit var yesRadioButton: RadioButton
    private lateinit var firstSnackReminderSpinner: Spinner
    private lateinit var secondSnackReminderSpinner: Spinner
    private lateinit var snackTextView: TextView

    private var userWantsSnackNotifications = false


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_results, container, false)
        initComponents(view)
        attatchListeners()
        setSpinners()
        updateResultMessage()
        setReminderOptionsVisibility()
        return view
    }

    private fun updateResultMessage() {
        var calculator: WaterConsumptionCalculator =
            arguments?.getSerializable("calculator") as WaterConsumptionCalculator
        resultsTextView.text =
            resultsTextView.text.toString().replace("___", calculator.calculateWater())
    }

    private fun setReminderOptionsVisibility() {
        var notificationsEnabled: Boolean? = arguments?.getBoolean("enableNotifications")

        if (notificationsEnabled!!) {
            sleepTimeSpinner.visibility = VISIBLE
            wakeUpTimeSpinner.visibility = VISIBLE
            remindersSpinner.visibility = VISIBLE
            timeSleepTextView.visibility = VISIBLE
            timeWakeUpTextView.visibility = VISIBLE
            remidersTextView.visibility = VISIBLE
            saveNotificationsButton.isClickable = true
            radioGroup.visibility = VISIBLE
        } else {
            sleepTimeSpinner.visibility = GONE
            wakeUpTimeSpinner.visibility = GONE
            remindersSpinner.visibility = GONE
            timeSleepTextView.visibility = GONE
            timeWakeUpTextView.visibility = GONE
            remidersTextView.visibility = GONE
            saveNotificationsButton.isClickable = false
            radioGroup.visibility = GONE
        }
    }

    private fun initComponents(view: View) {
        this.calculateAgainButton = view.findViewById(R.id.calculateAgainButton)
        this.saveNotificationsButton = view.findViewById(R.id.sendNotificationsButton)
        this.resultsTextView = view.findViewById(R.id.resultsTextView)
        this.wakeUpTimeSpinner = view.findViewById(R.id.wakeUpTimeSpinner)
        this.sleepTimeSpinner = view.findViewById(R.id.goToSleepTimeSpinner)
        this.remindersSpinner = view.findViewById(R.id.remindersSpinner)
        this.timeSleepTextView = view.findViewById(R.id.timeSleepTextView)
        this.timeWakeUpTextView = view.findViewById(R.id.timeWakeUpTextView)
        this.remidersTextView = view.findViewById(R.id.remidersTextView)
        this.radioGroup = view.findViewById(R.id.radioGroup)
        this.yesRadioButton = view.findViewById(R.id.yesRadioButton)
        this.noRadioButton = view.findViewById(R.id.noRadioButton)
        this.snackTextView = view.findViewById(R.id.snackReminderTextView)
        this.firstSnackReminderSpinner = view.findViewById(R.id.firstSnackReminderSpinner)
        this.secondSnackReminderSpinner = view.findViewById(R.id.secondSnackReminderSpinner)
    }



    private fun setSpinners() {
        setSpinnerAdapter(wakeUpTimeSpinner, R.array.wake_up_time)
        setSpinnerAdapter(sleepTimeSpinner, R.array.sleep_time)
        setSpinnerAdapter(remindersSpinner, R.array.reminders)
        setSpinnerAdapter(firstSnackReminderSpinner, R.array.first_snack_reminder_time)
        setSpinnerAdapter(secondSnackReminderSpinner, R.array.second_snack_reminder_time)
    }

    private fun setSpinnerAdapter(spinner: Spinner, textArrayResId: Int) {
        ArrayAdapter.createFromResource(
            activity as MainActivity,
            textArrayResId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun attatchListeners() {
        calculateAgainButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        saveNotificationsButton.setOnClickListener {
            saveNotifications()
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            showSnackReminderOptions(checkedId)
        }

    }

    private fun showSnackReminderOptions(checkedId: Int) {
        if (checkedId == R.id.yesRadioButton) {
            firstSnackReminderSpinner.visibility = VISIBLE
            secondSnackReminderSpinner.visibility = VISIBLE
            userWantsSnackNotifications = true
        } else {
            firstSnackReminderSpinner.visibility = GONE
            secondSnackReminderSpinner.visibility = GONE
            userWantsSnackNotifications = false
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNotifications() {
        if (userWantsSnackNotifications) {
            val reminder = Reminder(
                wakeUpTimeSpinner.selectedItem.toString(),
                sleepTimeSpinner.selectedItem.toString(),
                remindersSpinner.selectedItem.toString().toInt(),
                firstSnackReminderSpinner.selectedItem.toString(),
                secondSnackReminderSpinner.selectedItem.toString(),
                this.context
            )
            reminder.setReminderSchedule()
        }
        else {
            val reminder = Reminder(
                wakeUpTimeSpinner.selectedItem.toString(),
                sleepTimeSpinner.selectedItem.toString(),
                remindersSpinner.selectedItem.toString().toInt(),
                "",
                "",
                this.context
            )
            reminder.setReminderSchedule()
        }

    }

}