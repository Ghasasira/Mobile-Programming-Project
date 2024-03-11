package com.example.calcula

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier,
    onClick: () -> Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .then(modifier)

    ){
        Text(
            text = symbol,
            color = Color.Green,
            fontSize = 30.sp,
            )

    }
}

@Composable
fun CalcButton(
    label: String,
    //modifier: Modifier,
    onClick: () -> Unit
) {
    val buttonSize= (LocalConfiguration.current.screenWidthDp * 0.2)
    val numberSection= listOf<String>("1","2","3","4","5","6","7","8","9","0","+/-",".")

    Box(
        modifier = Modifier.padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors= CardDefaults.cardColors(if (label=="="){Color.Green}else{Color.DarkGray}),
            modifier = Modifier
                .clickable { onClick() }
                .size(buttonSize.dp)
                .clip(shape = CircleShape),
        ){}
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = if(label=="C"){Color.Red}else if(label in numberSection ){Color.White}else if(label=="="){Color.Black}else{Color.Green},
            fontSize = 32.sp,
            //color = Color.Red,

        )
    }
}