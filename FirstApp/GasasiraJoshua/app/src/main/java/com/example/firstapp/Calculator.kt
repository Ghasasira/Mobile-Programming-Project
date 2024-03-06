package com.example.firstapp

import androidx.compose.runtime.mutableStateOf

object Calculator {
    private var equation = mutableStateOf("")

    override fun toString(): String {
        return equation.value
    }

    fun appendSymbol(symbol: String) {
        equation.value += symbol
    }

    fun deleteLastSymbol() {
        equation.value = equation.value.dropLast(1)
    }

//    fun calculate() {
//        val result = Interpreter.calculate(equation.value)
//        equation.value = result?.toString() ?: "Error"
//    }
}