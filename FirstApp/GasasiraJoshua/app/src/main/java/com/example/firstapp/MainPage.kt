package com.example.firstapp

//import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Illuminant.C
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


//import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CalcFace() {
    val viewModel:CalcLogic = viewModel()
    val buttons = listOf<String>("C","()","%","/","7","8","9","x","4","5","6","-","1","2","3","+","+/-","0",".","=",)
    val screenHeight= LocalConfiguration.current.screenHeightDp

    var input by remember{ mutableStateOf(" ") }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(10.dp)
    ) {
       Box(
           modifier = Modifier.height((screenHeight * 0.3f).dp)
       ) {
           Column(
               verticalArrangement = Arrangement.SpaceAround,
               horizontalAlignment = Alignment.End,
               modifier = Modifier.fillMaxSize()
           ) {

               Text(
                   text = viewModel.expressions,
                   fontSize = 35.sp,
                   fontWeight = FontWeight.Bold
               )
               Text(
                   text = viewModel.resultString,
                   fontSize = 25.sp
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
                contentAlignment=Alignment.Center,
                //verticalArrangement = Arrangement.SpaceAround,
                //horizontalAlignment = Alignment.CenterHorizontally,
                modifier=Modifier.fillMaxSize(),
            ) {
                FlowRow{
                    buttons.forEach { item->
                        CalcButton(label = item)
                     }
                }
            }
        }
    }    
}

@Composable
fun CalcButton(label:String) {
    val viewModel:CalcLogic = viewModel()
    val buttonSize= (LocalConfiguration.current.screenWidthDp * 0.2)
    val numberSection= listOf<String>("1","2","3","4","5","6","7","8","9","0","+/-",".")

    Box(
        modifier = Modifier.padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors=CardDefaults.cardColors(if (label=="="){Color.Green}else{Color.DarkGray}),
            modifier = Modifier
                .clickable { viewModel.enterValue(label) }
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