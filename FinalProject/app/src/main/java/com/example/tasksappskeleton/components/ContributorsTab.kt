package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContributorTab() {
    Row (
        verticalAlignment = Alignment.Bottom,

        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
    ){
        UserAvatar()
        UserAvatar()
        UserAvatar()
        UserAvatar()
        UserAvatar()
        IconButton(
            onClick = { /*TODO*/ },
            ){
            Icon(imageVector = Icons.Rounded.AddCircle,contentDescription = "user avatar")
           }
    }
}

@Composable
fun UserAvatar() {
    Card(
        modifier = Modifier
            //.padding(5.dp)
            .size(40.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(color = Color.LightGray)
        ) {
//            Image(
//                painter = painterResource(id = profilePicResId),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "user avatar",
                tint = Color.Black,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
