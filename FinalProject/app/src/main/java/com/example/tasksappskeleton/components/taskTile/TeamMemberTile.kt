package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasksappskeleton.R
import com.example.tasksappskeleton.User
import com.example.tasksappskeleton.controllers.TaskViewModel

@Composable
fun TeamMemberTile(user:User,viewModel: TaskViewModel) {
    val screenHeight= LocalConfiguration.current.screenHeightDp
    Box(
        modifier = Modifier
            .height((0.15f * screenHeight).dp)
            .padding(bottom = 8.dp)
    ) {
        Card (modifier = Modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ){
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    //.fillMaxWidth(0.8f)
                    .fillMaxSize()
                    .padding(10.dp)

            ){

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)

            ){
                Box(

                    modifier = Modifier
                        .height((0.1f * screenHeight).dp)
                        .width((0.1f * screenHeight).dp)
                        .background(Color.White, shape = CircleShape)

                ) {
                    //Image(painter = painterResource(id = ), contentDescription = )
                    Image(
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = "Image from asset",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = user.name,
                        fontSize = 20.sp
                    )
                    Text(
                        text = user.role,
                        //fontSize = 16.sp
                    )
                }
                IconButton(onClick = {
                    viewModel.removeContributor(user)
                }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
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


@Composable
fun SelectableTeamMemberTile(user: User, userList: MutableList<User>) {
    val screenHeight= LocalConfiguration.current.screenHeightDp
    var selectionState = remember {
        mutableStateOf(false)
    }

    fun addOrRemoveMemberToSelection(user:User, userList: MutableList<User>){
        val existingUser = userList.find { it.name == user.name }
        if (existingUser == null) {
            userList.add(user)  // Add user if not found
        } else {
            userList.remove(existingUser)  // Remove user if found
        }
    }
//    select member function
    fun selectMember(){
        selectionState.value = !selectionState.value
        addOrRemoveMemberToSelection(user, userList)
    }

    Box(
        modifier = Modifier
            .height((0.15f * screenHeight).dp)
            .padding(bottom = 8.dp)

    ) {
        Card (modifier = Modifier.clickable {selectMember()},
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ){
            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    //.fillMaxWidth(0.8f)
                    .fillMaxSize()
                    .padding(10.dp)

            ){

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(end = 8.dp)
                        .fillMaxHeight()

                ) {
                    Box(

                        modifier = Modifier
                            .height((0.1f * screenHeight).dp)
                            .width((0.1f * screenHeight).dp)
                            .background(Color.White, shape = CircleShape)

                    ) {
                        //Image(painter = painterResource(id = ), contentDescription = )
                        Image(
                            painter = painterResource(R.drawable.avatar),
                            contentDescription = "Image from asset",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = user.name,
                            fontSize = 20.sp
                        )
                        Text(
                            text = user.role,
                            //fontSize = 16.sp
                        )
                    }

                }
                if(selectionState.value) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "selected",
                        tint = Color.Blue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

