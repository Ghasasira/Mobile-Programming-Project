package com.example.jetpacktwo

sealed class CalculatorOperations(val symbol: String) {
    object Add : CalculatorOperations("+")
    object Subtract : CalculatorOperations("-")
    object Multiply : CalculatorOperations("*")
    object Divide : CalculatorOperations("/")
//    object Clear : CalculatorOperations("C")
//    object Delete : CalculatorOperations("DEL")



}