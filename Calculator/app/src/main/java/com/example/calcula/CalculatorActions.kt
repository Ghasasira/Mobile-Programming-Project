package com.example.calcula

sealed class CalculatorActions {
    data class Number(val value: Int) : CalculatorActions()
    object Clear : CalculatorActions()
    object Delete : CalculatorActions()
    object Decimal : CalculatorActions()
    object Calculate : CalculatorActions()
    data class Operation(val operation: CalculatorOperations) : CalculatorActions()

}

