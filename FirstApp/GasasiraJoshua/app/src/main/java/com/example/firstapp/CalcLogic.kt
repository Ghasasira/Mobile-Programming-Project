package com.example.firstapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalcLogic:ViewModel() {
    var expressions by mutableStateOf(" ")
        private set
    var resultString by mutableStateOf(" ")
        private set

    fun enterValue(x:String){
        var enteredValue:String
        enteredValue = if(x=="x") {
            "*"
    //        }
    //        else if()
    //        {
        }else{
            x
        }
        expressions+x
    }

    fun clear(){}

    fun delete(){}

    fun calculate(){
        var result=expressions
        resultString=result.toString()
    }
}