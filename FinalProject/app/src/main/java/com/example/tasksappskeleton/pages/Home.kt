package com.example.tasksappskeleton.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tasksappskeleton.Screen
import com.example.tasksappskeleton.components.taskTile.CarouselList
import com.example.tasksappskeleton.components.taskTile.TaskFilterBar
import com.example.tasksappskeleton.controllers.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, viewModel: TaskViewModel) {

    val tasksViewModel: TaskViewModel= viewModel()
    val urgentTasksStateobserver=viewModel.selectedList.observeAsState()
    val loggedInUser=viewModel.loggedInUser
    val screenHeight = LocalConfiguration.current.screenHeightDp

//    fun selectTab(tabSelected:String){
//        selectedTab = tabSelected;
//        viewModel.selectListToShow(tabSelected)
//    }

    Scaffold (
        topBar = { TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Blue,
                ),
            actions = {
                      Box(
                          modifier = Modifier
                      ) {
                          Card(
                              onClick = { /*TODO*/ },
                              modifier = Modifier
                                  .padding(end = 8.dp)
                                  .size(35.dp),
                                  //.align(Alignment.Center),
                              colors = CardDefaults.cardColors(Color.White),
                              elevation = CardDefaults.cardElevation(10.dp),
                              shape = CircleShape,//CardDefaults.outlinedShape(CircleShape)
                          ) {
                              Box(
                                  modifier = Modifier.fillMaxSize()
                              ) {
                                  Icon(
                                      //modifier = Modifier.pad,
                                      imageVector = Icons.Outlined.Notifications,
                                      contentDescription = "notifications button",
                                      modifier = Modifier
                                          .size(30.dp)
                                          .align(Alignment.Center)
                                          .fillMaxSize(),
                                      tint=Color.Black
                                  )
                              }

                          }
//                          IconButton(onClick = { /*TODO*/ }) {
                              Icon(
                                  modifier= Modifier
                                      .align(Alignment.TopEnd)
                                      .size(18.dp),
                                  imageVector = Icons.Filled.Info,
                                  contentDescription = "notification",
                                  tint=Color.Yellow
                              )
//                          }
                      }
            },
            title = {
                Text(
                    text = "TaskKing",
                    fontWeight = FontWeight.Bold,
                    //fontSize = 24.sp,
                    color = Color.White
                )
            })},
    ){paddingValues ->Box(modifier = Modifier.padding(paddingValues)){
            LazyColumn(
                modifier=Modifier.fillMaxSize()
            ){

                item{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {


                            Column (
                                horizontalAlignment =  Alignment.Start,
                                modifier=Modifier.fillMaxSize()
                            ){
                                Text(
                                    text = "Hello ${loggedInUser?.name}",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    //color=Color.White
                                )
//                                Text(
//                                    text = "You may have some urgent tasks",
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color=Color.Red
//                                )
                            }


//                        if(
//                            urgentTasksStateobserver.value!!.isNotEmpty()
//                        ){
                        CarouselList(viewModel,navController)
//                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Column(
                            modifier = Modifier
                                .padding(10.dp)

                        ) {
                            TaskFilterBar(viewModel,navController)

                        }
                    }
                }
            }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.CreateTask.route)
                              },
                    //contentColor = Color.Blue,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 30.dp, bottom = 30.dp)
//                        .padding(20.dp)
                        .align(Alignment.BottomEnd)
                    //containerColor = FloatingActionButtonDefaults.containerColor  //shape(CircleShape)
                ) {
                    Card(
                        colors=CardDefaults.cardColors(Color.Blue),
                        elevation = CardDefaults.cardElevation(20.dp),
                        modifier = Modifier
                            .fillMaxSize()



                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ){
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint=Color.White,
                                modifier = Modifier.size(30.dp).align(Alignment.Center)
                            )
                        }
                    }

                }
           // }
        }
    }
    }


