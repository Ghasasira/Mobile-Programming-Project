package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasksappskeleton.controllers.TaskViewModel


@Composable
fun SubTaskTile(
    subTaskStatus:String,subTaskId:String,subTaskDescription:String,subTaskTitle:String,viewModel: TaskViewModel
) {
    val screenHeight= LocalConfiguration.current.screenHeightDp
    val screenWidth= LocalConfiguration.current.screenWidthDp
    Box(
        modifier = Modifier
            .height((0.2f * screenHeight).dp)
            .padding(bottom = 8.dp)
    ) {
        Card (modifier = Modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Column {
                        Text(
                            text = subTaskTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        // Spacer(modifier = Modifier.height(5.dp))
                        //Text("Timeline")
                    }
                   Text(
                        modifier = Modifier.padding(3.dp),
                        text = subTaskStatus,
                        color = if(subTaskStatus.lowercase()=="completed"){Color.Green}else{Color.Blue},
                        fontWeight = FontWeight.W500,
                        fontSize = 17.sp
                    )

                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    //verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                       // .padding(10.dp)

                ) {
                    Box(modifier =Modifier.width((0.75f*screenWidth).dp) ){
                        TruncateText(
                            text = subTaskDescription,
                            maxWords = 13
                        )
                    }

                    Column (){
                        if(subTaskStatus.lowercase()!="completed"){
                            IconButton(onClick = {
                                viewModel.updateSubTaskStatus(subTaskTitle)
                            }) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Done",
                                    tint = Color.Green
                                )
                            }
                        }
                        IconButton(onClick = {
                            viewModel.deleteSubtask(subTaskTitle)
                        }) {
                            Icon(
                                modifier = Modifier.size(
                                    if(subTaskStatus.lowercase()!="completed"){20.dp}else{30.dp}
                                    //15.dp
                                ),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }

            }
        }

    }
}


