package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tasksappskeleton.Screen
import com.example.tasksappskeleton.controllers.TaskViewModel

@Composable
fun CarouselList(viewModel: TaskViewModel,navController: NavController) {
    var urgentTaskState=viewModel.soonToExpireTasks.observeAsState()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between cards
    ) {
        item{
            urgentTaskState.value?.forEach {
                    task -> CarouselCard(task.taskTitle,task.taskId,viewModel,navController)
            }
        }
    }
}

@Composable
fun CarouselCard(taskName: String,taskId: String,viewModel: TaskViewModel,navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Card(
        modifier = Modifier
            .padding(20.dp)
            .width((0.75f * screenWidth).dp)
            .clickable {
                viewModel.getSingleTask(taskId)

                navController.navigate(Screen.TaskDetails.route) }
            .height(150.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color(0xFFADD8E6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),

        //backgroundColor = Color.Blue // Modify card background color as needed
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = taskName, color = Color.Black,fontSize = 30.sp) // Display text on card
            Text(text = "Due: 18 April 2024", color = Color.Red, modifier=Modifier, fontSize = 25.sp)
        }
    }
}