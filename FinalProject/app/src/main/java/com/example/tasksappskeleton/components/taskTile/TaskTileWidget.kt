package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tasksappskeleton.Screen
import com.example.tasksappskeleton.Task
//import com.example.tasksappskeleton.controllers.SingleTaskViewModel
import com.example.tasksappskeleton.controllers.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TaskTileWidget(navController: NavController, viewModel:TaskViewModel, task: Task) {
    fun formatSelectedDay(selectedDate: Date?): String {
        selectedDate ?: return "" // Return empty string if selectedDate is null

        val dateFormat = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())
        return dateFormat.format(selectedDate)
    }

    var startDate= task.startDate
    var endDate= task.endDate

    val screenHeight= LocalConfiguration.current.screenHeightDp
    //val singleTaskViewModel: SingleTaskViewModel = viewModel()
    Box(
        modifier = Modifier
            .height((0.25f * screenHeight).dp)
            .padding(bottom = 10.dp)
            .clickable {
                viewModel.getSingleTask(task.taskId)

                navController.navigate(Screen.TaskDetails.route)

            }
            //.padding(8.dp)
    ) {
        Card (modifier = Modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
        ){

            Column(
                verticalArrangement = Arrangement.SpaceAround,
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
                            text = task.taskTitle,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                       // Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            "$startDate - $endDate",
                            color = Color.Green,
                            fontSize = 12.sp
                            )
                    }
                    Text(text = task.status,color=if(task.status.lowercase()=="completed"){Color.Green}else{Color.Blue}, fontWeight = FontWeight.SemiBold)

                }
                Spacer(modifier = Modifier.height(8.dp))
                TruncateText(
                    text = task.description,
                    maxWords = 12
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.
                    fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "By Joshua", fontSize = 14.sp)
                    IconButton(onClick = { viewModel.deleteTask(task) }) {
                      Icon(
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
