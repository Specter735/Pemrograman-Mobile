package com.example.calculatortip

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val costOfService = findViewById<EditText>(R.id.cost_of_service)
        val tipOptions = findViewById<RadioGroup>(R.id.tip_options)
        val roundUpSwitch = findViewById<SwitchCompat>(R.id.round_up_switch)
        val calculateButton = findViewById<Button>(R.id.calculate_button)
        val tipResult = findViewById<TextView>(R.id.tip_result)

        calculateButton.setOnClickListener {
            val cost = costOfService.text.toString().toDoubleOrNull()
            if (cost == null) {
                tipResult.text = ""
                Toast.makeText(this, getString(R.string.cost_of_service_hint), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipPercentage = when (tipOptions.checkedRadioButtonId) {
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                else -> 0.15
            }

            var tip = cost * tipPercentage
            if (roundUpSwitch.isChecked) {
                tip = ceil(tip)
            }

            val formattedTip = getString(R.string.tip_result) + " $" + "%.2f".format(tip)
            tipResult.text = formattedTip
        }
    }
}
