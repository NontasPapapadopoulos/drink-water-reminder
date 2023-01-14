package com.nondaspap.drinkwaterreminder

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView


class ResultsFragment : Fragment() {

    private lateinit var calculateAgainButton: Button
    private lateinit var sendNotificationsButton: Button
    private lateinit var resultsTextView: TextView
    private lateinit var sleepTimeSpinner: Spinner
    private lateinit var wakeUpTimeSpinner: Spinner
    private lateinit var remindersSpinner: Spinner
    private lateinit var timeWakeUpTextView: TextView
    private lateinit var timeSleepTextView:TextView
    private lateinit var remidersTextView:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_results, container, false)
        initComponents(view)
        attatchListeners()
        setSpinners()
        updateResultMessage()
        setReminderOptionsVisibility()
        return view
    }

    private fun updateResultMessage() {
        var calculator: WaterConsumptionCalculator = arguments?.getSerializable("calculator") as WaterConsumptionCalculator
        resultsTextView.text = resultsTextView.text.toString().replace("___", calculator.calculateWater())
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
            sendNotificationsButton.isClickable = true
        }
        else {
            sleepTimeSpinner.visibility = GONE
            wakeUpTimeSpinner.visibility = GONE
            remindersSpinner.visibility = GONE
            timeSleepTextView.visibility = GONE
            timeWakeUpTextView.visibility = GONE
            remidersTextView.visibility = GONE
            sendNotificationsButton.isClickable = false
        }
    }

    private fun initComponents(view: View) {
        this.calculateAgainButton = view.findViewById(R.id.calculateAgainButton)
        this.sendNotificationsButton = view.findViewById(R.id.sendNotificationsButton)
        this.resultsTextView = view.findViewById(R.id.resultsTextView)
        this.wakeUpTimeSpinner = view.findViewById(R.id.wakeUpTimeSpinner)
        this.sleepTimeSpinner = view.findViewById(R.id.goToSleepTimeSpinner)
        this.remindersSpinner = view.findViewById(R.id.remindersSpinner)
        this.timeSleepTextView = view.findViewById(R.id.timeSleepTextView)
        this.timeWakeUpTextView = view.findViewById(R.id.timeWakeUpTextView)
        this.remidersTextView = view.findViewById(R.id.remidersTextView)
    }

    private fun setSpinners() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            activity as MainActivity,
            R.array.wake_up_time,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            wakeUpTimeSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            activity as MainActivity,
            R.array.sleep_time,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            sleepTimeSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            activity as MainActivity,
            R.array.reminders,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            remindersSpinner.adapter = adapter
        }
    }

    private fun attatchListeners() {
        calculateAgainButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()


        sendNotificationsButton.setOnClickListener {

        }
        }
    }




}