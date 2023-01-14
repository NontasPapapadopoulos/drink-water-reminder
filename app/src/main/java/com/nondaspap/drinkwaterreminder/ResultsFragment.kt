package com.nondaspap.drinkwaterreminder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class ResultsFragment : Fragment() {

    private lateinit var calculateAgainButton: Button
    private lateinit var sendNotificationsButton: Button
    private lateinit var resultsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_results, container, false)
        initComponents(view)
        attatchListeners()
        updateResultMessage()
        return view
    }

    private fun updateResultMessage() {
        var calculator: WaterConsumptionCalculator = arguments?.getSerializable("calculator") as WaterConsumptionCalculator
        resultsTextView.text = resultsTextView.text.toString().replace("___", calculator.calculateWater())
    }

    private fun initComponents(view: View) {
        this.calculateAgainButton = view.findViewById(R.id.calculateAgainButton)
        this.sendNotificationsButton = view.findViewById(R.id.sendNotificationsButton)
        this.resultsTextView = view.findViewById(R.id.resultsTextView)
    }

    private fun attatchListeners() {
        calculateAgainButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()

        }
    }




}