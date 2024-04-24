package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(title: String) {
    TopAppBar(title = { /*TODO*/ },
        Modifier.background(color = Color.Blue,),
        )
//    TopAppBar(
//        title = { Text(text = title) },
//        backgroundColor = Color.Blue, // Set the background color
//        contentColor = Color.White // Set the content color (title, icons, etc.)
//    )
}