package com.example.tasksappskeleton.components.taskTile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tasksappskeleton.controllers.TaskViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskFilterBar(viewModel: TaskViewModel,navController: NavController) {

    var numberOfTasks: MutableState<Int> = mutableIntStateOf(0)
    val tasksStateobserver=viewModel.tasks.observeAsState()
    val toDoStateobserver=viewModel.toDoTasks.observeAsState()
    val completedStateobserver=viewModel.completedTasks.observeAsState()
    val expiredStateobserver=viewModel.expiredTasks.observeAsState()
    val filterTags= listOf("All","ToDo","Completed", "Past")
    var selectedTab =viewModel.selectedList.observeAsState(initial = "All")



    fun selectTab(tabSelected:String){
        viewModel.selectList(tabSelected)
    }

    Row (
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
    ){
        Text(
            text =if(tasksStateobserver.value?.isNotEmpty()==true){
                "Tasks ${numberOfTasks.value}"
            }else{ "Tasks" },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    //TaskFilterBar(viewModel)
    LazyRow {

        item {

            filterTags.forEach { tag ->
                TextButton(onClick = {
                    selectTab(tag)

                }) {
                    Text(
                        text = tag,
                        fontSize = 18.sp,
                        style = TextStyle(),
                        color = if (
                            tag == selectedTab.value
                        ) {
                            Color.Blue
                        } else {
                            Color.Gray
                        }

                    )
                }
            }   }

    }

    Spacer(modifier = Modifier.height(5.dp))
    if(selectedTab.value=="All"){
        if (tasksStateobserver.value?.isEmpty() == false) {
            tasksStateobserver.value?.forEach {
                    task->TaskTileWidget(
                        navController,
                        viewModel,
                        task
                    )
            }
        }else{
            Text(text = "No tasks yet.")
        }
    }else if(selectedTab.value=="ToDo"){
        if (toDoStateobserver.value?.isEmpty() == false) {
            toDoStateobserver.value?.forEach {
                    task-> TaskTileWidget(
                        navController,
                        viewModel,
                        task
                    )
            }
        }else{
            Text(text = "No tasks yet.")
        }
    }else if(selectedTab.value=="Completed"){
        if (completedStateobserver.value?.isEmpty() == false) {
            completedStateobserver.value?.forEach {
                    task-> TaskTileWidget(
                        navController,
                        viewModel,
                        task
                    )
            }
        }else{
            Text(text = "No tasks yet.")
        }
    }else if(selectedTab.value=="Past"){
        if (expiredStateobserver.value?.isEmpty() == false) {
            expiredStateobserver.value?.forEach {
                    task-> TaskTileWidget(
                        navController,
                        viewModel,
                        task
                    )
            }
        }else{
            Text(text = "No tasks yet.")
        }
    }

}
