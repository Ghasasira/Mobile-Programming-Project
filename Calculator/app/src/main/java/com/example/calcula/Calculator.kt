package com.example.calcula

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.calcula.ui.theme.blue1
import com.example.calcula.ui.theme.blue2
import com.example.calcula.ui.theme.green1

@Composable
fun Calculator(
    state: CalculatorState,
    buttonSpacing: Dp = 8.dp,
    modifier: Modifier = Modifier,
    onAction: (CalculatorActions) -> Unit

    ){
    val screenHeight= LocalConfiguration.current.screenHeightDp
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
        ){
          Box(
              modifier = Modifier.height((screenHeight*0.3f).dp)
          ) {
              Text(
                  text = state.number1 + (state.operation?.symbol?: "") + state.number2,
                  textAlign = TextAlign.End,
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(vertical = 24.dp),
                  fontWeight = FontWeight.Light,
                  fontSize = 40.sp,
                  color = Color.White,
                  maxLines = 3
              )
          }
            Box(modifier = Modifier.height((screenHeight*0.67f).dp)){
                Column (
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxSize()){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    ){
                        CalculatorButton(
                            symbol = "AC",
                            modifier = Modifier
                                .background(green1)
                                .aspectRatio(2f)
                                .weight(2f),
                            onClick ={
                                onAction(CalculatorActions.Clear)
                            }
                        )
                        CalculatorButton(
                            symbol = "Del",
                            modifier = Modifier
                                .background(green1)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Delete)
                            }
                        )
                        CalculatorButton(
                            symbol = "/",
                            modifier = Modifier
                                .background(blue1)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Operation(CalculatorOperations.Divide))
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    ){
                        CalculatorButton(
                            symbol = "7",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(7))
                            }
                        )
                        CalculatorButton(
                            symbol = "8",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(8))
                            }
                        )
                        CalculatorButton(
                            symbol = "9",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(9))
                            }
                        )
                        CalculatorButton(
                            symbol = "x",
                            modifier = Modifier
                                .background(blue1)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Operation(CalculatorOperations.Multiply))
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    ){
                        CalculatorButton(
                            symbol = "4",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(4))
                            }
                        )
                        CalculatorButton(
                            symbol = "5",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(5))
                            }
                        )
                        CalculatorButton(
                            symbol = "6",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(6))
                            }
                        )
                        CalculatorButton(
                            symbol = "-",
                            modifier = Modifier
                                .background(blue1)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Operation(CalculatorOperations.Subtract))
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    ){
                        CalculatorButton(
                            symbol = "1",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(1))
                            }
                        )
                        CalculatorButton(
                            symbol = "2",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(2))
                            }
                        )
                        CalculatorButton(
                            symbol = "3",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(3))
                            }
                        )
                        CalculatorButton(
                            symbol = "+",
                            modifier = Modifier
                                .background(blue1)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Operation(CalculatorOperations.Add))
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    ){
                        CalculatorButton(
                            symbol = "-/+",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(1))
                            }
                        )
                        CalculatorButton(
                            symbol = "0",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Number(0))
                            }
                        )
                        CalculatorButton(
                            symbol = ".",
                            modifier = Modifier
                                .background(blue2)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Decimal)
                            }
                        )
                        CalculatorButton(
                            symbol = "=",
                            modifier = Modifier
                                .background(blue1)
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick ={
                                onAction(CalculatorActions.Calculate)
                            }
                        )
                    }


                }
            }
          }
    }
}


