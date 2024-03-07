package com.example.calcula

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalcluatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())

        private  set
    fun onAction(actions: CalculatorActions){

        when(actions){
            is CalculatorActions.Number -> enterNumber(actions.value)
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> state = CalculatorState()
            is CalculatorActions.Operation -> enterOperation(actions.operation)
            is CalculatorActions.Calculate -> perfromCalculation()
            is CalculatorActions.Delete -> performDeletion()
        }
    }

    private fun performDeletion() {

        when{
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null,
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun perfromCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

      if(number1 != null && number2 != null) {
            val result = when(state.operation){
                is CalculatorOperations.Add -> number1 + number2
                is CalculatorOperations.Subtract -> number1 - number2
                is CalculatorOperations.Multiply -> number1 * number2
                is CalculatorOperations.Divide -> number1 / number2
                null -> return
            }
          state = state.copy(
              number1 = result.toString().take(15),
              number2 = "",
              operation = null
          )


        }
    }


    private fun enterOperation(operation: CalculatorOperations) {
        if(state.number1.isNotBlank()){
            state = state.copy(operation = operation)
        }

    }

    private fun enterDecimal() {
       if (state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()){
           state = state.copy(
               number1 = state.number1 + "."
           )
           return
       }
        if (!state.number2.contains(".") && state.number2.isNotBlank()){
            state = state.copy(
                number1 = state.number2 + "."
            )

        }
    }

    private fun enterNumber(value: Int) {
       if(state.operation == null) {
           if(state.number1.length >= MAX_NUMBER_LENGTH){
               return
           }
           state = state.copy(
               number1 =  state.number1 + value
           )
           return
       }
        if(state.number2.length >= MAX_NUMBER_LENGTH){
            return
        }
        state = state.copy(
            number2 =  state.number2 + value
        )


    }
    companion object{
        private  const val MAX_NUMBER_LENGTH = 14
    }
}