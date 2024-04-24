package com.example.tasksappskeleton.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasksappskeleton.Task
import com.example.tasksappskeleton.User
import com.example.tasksappskeleton.components.taskTile.SelectableTeamMemberTile
import com.example.tasksappskeleton.controllers.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteMembers(navController:NavController, viewModel: TaskViewModel, task:Task) {
    val userObserver=viewModel.users.observeAsState()
    var contributors = mutableListOf<User>()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    ),
                title = { Text(text = "Add Team Members", color = Color.White) },
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
        bottomBar={
            BottomAppBar (
                modifier=Modifier.height(70.dp),
                tonalElevation =  10.dp

            ){

//                colors = BottomAppBarDefaults.bottomAppBarColors(
//                    containerColor = Color.Blue,
//                    ),
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    onClick = {

                              viewModel.addContributor(task, contributors)
                         },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp, start = 12.dp),
                ) {
                    Text("Invite")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                Column (
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ){
                    SearchBar (onSearch = {})
                    Spacer(modifier=Modifier.height(10.dp))
                    if(
                        userObserver.value?.isEmpty() ==true

                    ){
                        Text("No Users to show")
                    }else{
                        userObserver.value?.forEach { user ->
                            SelectableTeamMemberTile(
                                user,
                                contributors
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun SearchBar(
    onSearch: () -> Unit
) {
    var query = remember { mutableStateOf("") }


        OutlinedTextField(
            leadingIcon = {Icon(Icons.Filled.Search, contentDescription = "Search")},
            value = query.value,
            onValueChange = {/*TO DO*/},
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {Text(text = "Search")}
        )
}
