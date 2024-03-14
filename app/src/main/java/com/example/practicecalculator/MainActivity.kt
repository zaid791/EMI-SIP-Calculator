package com.example.practicecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlin.math.pow
import com.google.android.material.slider.Slider
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        container = findViewById(R.id.container)

        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)


        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                // Handle button checked event
                showContentForButton(checkedId)
            }
        }
        //Initial State
        toggleGroup.check(R.id.button1)
        showContentForButton(R.id.button1)
    }
    fun formatNumber(number: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
        return format.format(number)
    }
    private fun setupSeekBarWithLabelEMI(
        slider1: Slider,
        slider2: Slider,
        slider3: Slider,
        valueLabel1: TextView,
        valueLabel2: TextView,
        valueLabel3: TextView,
        monthlyEMI: TextView,
        tVPrincipalAmount: TextView,
        tVTotalInterest: TextView,
        tVTotalAmount: TextView,
        initialValue: Int
    ) {
        // Set the initial value on the label
        valueLabel1.text = "$initialValue"

        slider1.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Handle touch start event
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Handle touch stop event
            }
        })

        slider1.addOnChangeListener { _, value, fromUser ->
            // Handle value change event
            if (fromUser) {
                // The change is initiated by the user
                valueLabel1.text = formatNumber(value.toDouble())
                tVPrincipalAmount.text = formatNumber(value.toDouble())

                // Update the result dynamically
                val principal = value.toDouble()
                val interestRate = slider2.value.toDouble()
                val tenure = slider3.value.toInt()
                val emi = calculateEMI(principal, interestRate, tenure)
                val totalInterest = calculateTotalInterest(principal, emi, tenure)
                val totalAmount = calculateTotalAmount(principal, totalInterest)
                "${formatNumber(emi)}".also { monthlyEMI.text = it }
                "${formatNumber(totalInterest)}".also { tVTotalInterest.text = it }
                "${formatNumber(totalAmount)}".also { tVTotalAmount.text = it }
            }
        }
        slider2.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Handle touch start event if needed
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Handle touch stop event if needed
            }
        })

        slider2.addOnChangeListener { _, value, fromUser ->
            // Handle value change event
            if (fromUser) {
                // Update the TextView dynamically as the Slider value changes
                valueLabel2.text = value.toInt().toString() + "%"

                val principal = slider1.value.toDouble()
                val interestRate = value.toDouble()
                val tenure = slider3.value.toInt()
                val emi = calculateEMI(principal, interestRate, tenure)
                val totalInterest = calculateTotalInterest(principal, emi, tenure)
                val totalAmount = calculateTotalAmount(principal, totalInterest)
                "${formatNumber(emi)}".also { monthlyEMI.text = it }
                "${formatNumber(totalInterest)}".also { tVTotalInterest.text = it }
                "${formatNumber(totalAmount)}".also { tVTotalAmount.text = it }
            }
        }
        slider3.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Handle touch start event if needed
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Handle touch stop event if needed
            }
        })

        slider3.addOnChangeListener { _, value, fromUser ->
            // Handle value change event
            if (fromUser) {
                // Update the TextView dynamically as the Slider value changes
                value.toInt()
                valueLabel3.text = value.toInt().toString() + "Yr"

                val principal = slider1.value.toDouble()
                val interestRate = slider2.value.toDouble()
                val tenure = value.toInt()
                val emi = calculateEMI(principal, interestRate, tenure)
                val totalInterest = calculateTotalInterest(principal, emi, tenure)
                val totalAmount = calculateTotalAmount(principal, totalInterest)

                "${formatNumber(emi)}".also { monthlyEMI.text = it }
                "${formatNumber(totalInterest)}".also { tVTotalInterest.text = it }
                "${formatNumber(totalAmount)}".also { tVTotalAmount.text = it }
            }
        }
    }

private fun setupSeekBarWithLabelSIP(
    slider1: Slider,
    slider2: Slider,
    slider3: Slider,
    valueLabel1: TextView,
    valueLabel2: TextView,
    valueLabel3: TextView,
    tVInvestedAmount: TextView,
    tVEstReturns: TextView,
    tVTotalValue: TextView,
    initialValue: Int
) {
    // Set the initial value on the label
    valueLabel1.text = "$initialValue"

    slider1.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: Slider) {
            // Handle touch start event
        }

        override fun onStopTrackingTouch(slider: Slider) {
            // Handle touch stop event
        }
    })

    slider1.addOnChangeListener { _, value, fromUser ->
        // Handle value change event
        if (fromUser) {
            // The change is initiated by the user
            valueLabel1.text = formatNumber(value.toDouble())

            // Update the result dynamically
            val monthlyInvestment = value.toDouble()
            val annualReturnRate = slider2.value.toDouble()
            val years = slider3.value.toInt()

            val investedAmount = calculateInvestedAmount(monthlyInvestment, years)
            val estimatedReturns = calculateEstimatedReturns(monthlyInvestment, annualReturnRate, years)
            val totalValue = calculateTotalValue(monthlyInvestment, annualReturnRate, years)

            "${formatNumber(investedAmount)}".also { tVInvestedAmount.text = it }
            "${formatNumber(estimatedReturns)}".also { tVEstReturns.text = it }
            "${formatNumber(totalValue)}".also { tVTotalValue.text = it }
        }
    }

    slider2.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: Slider) {
            // Handle touch start event if needed
        }

        override fun onStopTrackingTouch(slider: Slider) {
            // Handle touch stop event if needed
        }
    })

    slider2.addOnChangeListener { _, value, fromUser ->
        // Handle value change event
        if (fromUser) {
            // Update the TextView dynamically as the Slider value changes
            valueLabel2.text = value.toInt().toString() + "%"

            val monthlyInvestment = slider1.value.toDouble()
            val annualReturnRate = value.toDouble()
            val years = slider3.value.toInt()

            val investedAmount = calculateInvestedAmount(monthlyInvestment, years)
            val estimatedReturns = calculateEstimatedReturns(monthlyInvestment, annualReturnRate, years)
            val totalValue = calculateTotalValue(monthlyInvestment, annualReturnRate, years)

            "${formatNumber(investedAmount)}".also { tVInvestedAmount.text = it }
            "${formatNumber(estimatedReturns)}".also { tVEstReturns.text = it }
            "${formatNumber(totalValue)}".also { tVTotalValue.text = it }
        }
    }

    slider3.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: Slider) {
            // Handle touch start event if needed
        }

        override fun onStopTrackingTouch(slider: Slider) {
            // Handle touch stop event if needed
        }
    })

    slider3.addOnChangeListener { _, value, fromUser ->
        // Handle value change event
        if (fromUser) {
            // Update the TextView dynamically as the Slider value changes
            valueLabel3.text = value.toInt().toString() + "Yr"

            val monthlyInvestment = slider1.value.toDouble()
            val annualReturnRate = slider2.value.toDouble()
            val years = value.toInt()

            val investedAmount = calculateInvestedAmount(monthlyInvestment, years)
            val estimatedReturns = calculateEstimatedReturns(monthlyInvestment, annualReturnRate, years)
            val totalValue = calculateTotalValue(monthlyInvestment, annualReturnRate, years)

            "${formatNumber(investedAmount)}".also { tVInvestedAmount.text = it }
            "${formatNumber(estimatedReturns)}".also { tVEstReturns.text = it }
            "${formatNumber(totalValue)}".also { tVTotalValue.text = it }
        }
    }
}

    //SIP Logic
    private fun calculateInvestedAmount(
        monthlyInvestment: Double,
        years: Int
    ): Double {
        return monthlyInvestment * years * 12
    }
    private fun calculateEstimatedReturns(
        monthlyInvestment: Double,
        annualReturnRate: Double,
        years: Int
    ): Double {
        val monthlyInterestRate = annualReturnRate / 12 / 100
        val totalMonths = years * 12
        val compoundingFactor = (1 + monthlyInterestRate).pow(totalMonths.toDouble())

        val futureValue = monthlyInvestment * ((compoundingFactor - 1) / monthlyInterestRate)
        return futureValue
    }
    private fun calculateTotalValue(
        monthlyInvestment: Double,
        annualReturnRate: Double,
        years: Int
    ): Double {
        val monthlyInterestRate = annualReturnRate / 12 / 100
        val totalMonths = years * 12
        val compoundingFactor = (1 + monthlyInterestRate).pow(totalMonths.toDouble())

        return monthlyInvestment * ((compoundingFactor - 1) / monthlyInterestRate)
    }
    //EMI Logic
    private fun calculateEMI(
        principal: Double,
        annualInterestRate: Double,
        tenureInYears: Int
    ): Double {
        val monthlyInterestRate = annualInterestRate / 12 / 100
        val numberOfPayments = tenureInYears * 12.toDouble()
        return ((principal * (monthlyInterestRate * (1 + monthlyInterestRate).pow(numberOfPayments)) /
                ((1 + monthlyInterestRate).pow(numberOfPayments) - 1)))
    }
    private fun calculateTotalInterest(principal: Double, emi: Double, tenureInYears: Int): Double {
        return emi * tenureInYears * 12 - principal
    }
    private fun calculateTotalAmount(principal: Double, totalInterest: Double): Double {
        return principal + totalInterest
    }

    private fun showContentForButton(checkedId: Int) {
        // Clear existing views in the container
        container.removeAllViews()

        // Inflate and add the corresponding view based on the checkedId
        val inflater = LayoutInflater.from(this)
        val view = when (checkedId) {
            R.id.button1 -> inflater.inflate(R.layout.view_option1, container, false)
            R.id.button2 -> inflater.inflate(R.layout.view_option2, container, false)
            // Add more cases for other buttons as needed
            else -> throw IllegalArgumentException("Invalid button ID")
        }

        //view option 1
        val tVMonthlyEMI = view.findViewById<TextView>(R.id.tvMonthlyEmi)
        val tVPrincipalAmount = view.findViewById<TextView>(R.id.tvPAmount)
        val tVTotalInterest = view.findViewById<TextView>(R.id.tvTotalInterest)
        val tVTotalAmount = view.findViewById<TextView>(R.id.tvTotalAmount)

        //view option 2
        val tVInvestedAmount = view.findViewById<TextView>(R.id.tvInvestedAmount)
        val tVEstReturns = view.findViewById<TextView>(R.id.tvEstReturns)
        val tVTotalValue = view.findViewById<TextView>(R.id.tvTotalValue)

        // Find the SeekBars and TextViews in the inflated view
        val sliderOption1 = view.findViewById<Slider>(R.id.sliderOption1)
        val sliderOption2 = view.findViewById<Slider>(R.id.sliderOption2)
        val sliderOption3 = view.findViewById<Slider>(R.id.sliderOption3)

        val valueLabelOption1 = view.findViewById<TextView>(R.id.valueLabelOption1)
        val valueLabelOption2 = view.findViewById<TextView>(R.id.valueLabelOption2)
        val valueLabelOption3 = view.findViewById<TextView>(R.id.valueLabelOption3)

        // Set up SeekBars with labels
        when(checkedId) {
            R.id.button1 -> {
                setupSeekBarWithLabelEMI(
                    sliderOption1,
                    sliderOption2,
                    sliderOption3,
                    valueLabelOption1,
                    valueLabelOption2,
                    valueLabelOption3,
                    tVMonthlyEMI,
                    tVPrincipalAmount,
                    tVTotalInterest,
                    tVTotalAmount,
                    initialValue = 0
                )
            }

            R.id.button2 -> {
                setupSeekBarWithLabelSIP(
                    sliderOption1,
                    sliderOption2,
                    sliderOption3,
                    valueLabelOption1,
                    valueLabelOption2,
                    valueLabelOption3,
                    tVInvestedAmount,
                    tVEstReturns,
                    tVTotalValue,
                    initialValue = 0
                )
            }
        }

        container.addView(view)

        // Set the visibility of the added view to VISIBLE
        view.visibility = View.VISIBLE
    }
}