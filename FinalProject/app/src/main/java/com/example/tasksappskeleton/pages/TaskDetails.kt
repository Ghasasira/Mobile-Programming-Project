package com.example.tasksappskeleton.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tasksappskeleton.Screen
import com.example.tasksappskeleton.Task
import com.example.tasksappskeleton.components.taskTile.CommentsDisplay
import com.example.tasksappskeleton.components.taskTile.ContributorTab
import com.example.tasksappskeleton.components.taskTile.FilesDisplay
import com.example.tasksappskeleton.components.taskTile.SubTaskTile
import com.example.tasksappskeleton.components.taskTile.SubtasksDisplay
import com.example.tasksappskeleton.components.taskTile.TaskDetailsFilterBar
import com.example.tasksappskeleton.components.taskTile.TeamDisplay
import com.example.tasksappskeleton.components.taskTile.TruncateText
//import com.example.tasksappskeleton.controllers.SingleTaskViewModel
import com.example.tasksappskeleton.controllers.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetails(navController: NavController, viewModel: TaskViewModel) {
    val filterTags= listOf<String>("Sub tasks","Files","Team")
    var selectedTab by remember { mutableStateOf("Sub tasks") }
    val userObserver= viewModel.users.observeAsState()
    val taskObserver= viewModel.selectedTask.observeAsState()

    fun selectTab(tabSelected:String){
        selectedTab = tabSelected;
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp
//    val completionStatus=viewModel.percentageCompleted.observeAsState()
    var percentage= viewModel.percentageCompleted.observeAsState()
    var selectedTask: Task = taskObserver.value!!;

    fun formatSelectedDay(selectedDate: Date?): String {
        selectedDate ?: return "" // Return empty string if selectedDate is null

        val dateFormat = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())
        return dateFormat.format(selectedDate)
    }

    var startDate= taskObserver.value!!.startDate
    var endDate= taskObserver.value!!.endDate

    val percentageValue = percentage.value!! * 100
    val formattedPercentage = String.format("%.1f%%", percentageValue)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,

                    ),
                title = {
                    Text(
                        text = taskObserver.value?.taskTitle ?: "",
                        //text = taskObserver.value?.taskId.toString(), //?: "No task selected.", //tasksViewModel.selectedTask?.taskTitle.toString() ,
                        fontWeight = FontWeight.Bold,
                        //fontSize = 30.sp,
                        color = Color.White
                    )
                },
                actions = {
                          Row {
                              IconButton(onClick = { })
                              {
                                  Icon(
                                      imageVector = Icons.Default.MoreVert,
                                      tint = Color.White,
                                      contentDescription = "navigate back"
                                  )
                              }
                          }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back button",
                            tint = Color.White
                        )
                    }
                }
            )
        },
    ) { paddingValues-> LazyColumn(

        modifier=Modifier.padding(paddingValues)
    ){

        item{
            Column {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        //.height(screenHeight.dp)
                        .fillMaxSize()
                ) {
                    Column (
                        modifier=Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)

                        ){
                           // if (percentage != null) {
                                CircularProgressIndicator(
                                    progress = {
                                               //0.65f
                                        percentage.value!!.toFloat()
                                    },
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    color = Color.Green,
                                    strokeWidth = 10.dp,
                                    trackColor = Color.Gray,

                                    )
                            Text(
                                text = formattedPercentage,
                                fontSize = 22.sp,
                                color=Color.Green,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Starting date:${startDate}",
                            modifier=Modifier,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Ending date:${endDate}",
                            modifier=Modifier,
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    //Text(text = "Description",)
                    Text(
                        fontSize = 16.sp,
                        text = taskObserver.value?.description ?: "No Description to show",
                        //"Task Description, this tends to be a little longer than normal text so I'm hustling to get stuff to write and this seems not to be working",
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    TaskDetailsFilterBar(
                        modifier = Modifier.fillMaxWidth(),
                        filterTags = filterTags,
                        selectedTab = selectedTab,
                        onTabSelected = { selectTab(it) }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (selectedTab=="Sub tasks"){
                        SubtasksDisplay(viewModel, selectedTask)
                    }else if(selectedTab=="Files"){
                        FilesDisplay(
                            navigateFunc = {navController.navigate(Screen.InviteMembers.route)},
                            viewModel
                            )
                    }else if(selectedTab=="Team"){
                        TeamDisplay(
                            navigateFunc = {
                                viewModel.fetchUsers()
                                navController.navigate(Screen.InviteMembers.route)
                                           },
                            viewModel
                        )
//                    }else{
//                        CommentsDisplay()
                    }
                }
            }
        }
    }

    }
}


//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBarDetails() {
//    TopAppBar(
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = Color.Blue,
//
//            ),
//        title = { Text(text = "Add New Task", color = Color.White)},
//        navigationIcon = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "back button",
//                    tint = Color.White
//                )
//            }
//        }
//    )
//}