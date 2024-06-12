package com.example.ppab_11_l0122018_alyzakhoirunnadif.ui.calculator

import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var result = ""

    fun calculate(firstNumber: Double, secondNumber: Double, operator: Char) {
        val resultDouble = when (operator) {
            '+' -> firstNumber + secondNumber
            '-' -> firstNumber - secondNumber
            '*' -> firstNumber * secondNumber
            '/' -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
            else -> Double.NaN
        }

        result = if (resultDouble % 1 == 0.0) {
            resultDouble.toInt().toString()
        } else {
            resultDouble.toString()
        }
    }
}