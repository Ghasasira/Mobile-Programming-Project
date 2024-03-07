package com.example.calcula

sealed class CalculatorOperations(val symbol: String) {
    object Add : CalculatorOperations("+")
    object Subtract : CalculatorOperations("-")
    object Multiply : CalculatorOperations("*")
    object Divide : CalculatorOperations("/")
//    object Clear : CalculatorOperations("C")
//    object Delete : CalculatorOperations("DEL")



}