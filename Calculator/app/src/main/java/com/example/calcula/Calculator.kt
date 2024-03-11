package com.example.calcula

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Calculator(
    state: CalculatorState,
    answer: String,//State<String>,
    modifier: Modifier = Modifier,
    onAction: (CalculatorActions) -> Unit

    ){
    val coroutineScope = rememberCoroutineScope()
    val buttons = listOf<String>("C","%","Del","/","7","8","9","x","4","5","6","-","1","2","3","+","+/-","0",".","=",)
    val numpad = listOf<String>("7","8","9","4","5","6","1","2","3","0")
    val screenHeight= LocalConfiguration.current.screenHeightDp
//    val displayedAnswer by remember {
//        derivedStateOf { answer.answer }
//    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),


    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Box(
                modifier = Modifier.height((screenHeight * 0.3f).dp)
            ) {
                Column (

                    verticalArrangement = Arrangement.SpaceAround
                ){
                    Text(
                        text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        fontWeight = FontWeight.Light,
                        fontSize =26.sp,
                        color = Color.White,
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = answer ,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 32.sp,
                        color = Color.White,
                        maxLines = 2
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Divider(Modifier.fillMaxWidth(), thickness = 2.dp)
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .height((screenHeight * 0.65f).dp)
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    FlowRow {
                        buttons.forEach { item ->
                            CalcButton(
                                label = item,
                                onClick = {
                                    when (item) {
                                        "x"-> onAction(CalculatorActions.Operation(CalculatorOperations.Multiply))
                                        "/"-> onAction(CalculatorActions.Operation(CalculatorOperations.Divide))
                                        "+"-> onAction(CalculatorActions.Operation(CalculatorOperations.Add))
                                        "-"-> onAction(CalculatorActions.Operation(CalculatorOperations.Subtract))
                                        "="-> onAction(CalculatorActions.Calculate)
                                        "Del"-> onAction(CalculatorActions.Delete)
                                        "C"-> onAction(CalculatorActions.Clear)
                                        "."-> onAction(CalculatorActions.Decimal)
                                        in numpad ->{
                                            onAction(CalculatorActions.Number(item.toInt()))
                                        }
                                        else -> println("TODO")
                                    }
                                    //onAction(CalculatorActions.Number(7))
                                          },
                            )

                            }
                    }
                }
            }
        }
    }
}


