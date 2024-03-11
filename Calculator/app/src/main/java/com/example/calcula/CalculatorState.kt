package com.example.calcula

data class CalculatorState(
    var number1 : String = "",
    val number2 : String = "",
    val operation: CalculatorOperations? = null,
)

data class CalculatorAnswer(
    var answer: String =""
)