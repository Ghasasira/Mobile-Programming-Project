package com.example.tasksappskeleton.components.taskTile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasksappskeleton.R
import com.example.tasksappskeleton.controllers.TaskViewModel

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Composable
fun AttachmentTile(file:String,viewModel: TaskViewModel) {
    var context= LocalContext.current
    val screenHeight= LocalConfiguration.current.screenHeightDp
    Box(
        modifier = Modifier
            .height((0.12f * screenHeight).dp)
            .padding(bottom = 10.dp)
    ) {
        Card (modifier = Modifier
            .clickable {
                val pdfUrl =file
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(intent)

            },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ){
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)

            ){
                Box(
                    modifier = Modifier
//                        .height((0.1f * screenHeight).dp)
//                        .size(50.dp)
//                        .background(Color.Gray)
                ){
                    Image(painter = painterResource(R.drawable.file), contentDescription = "file icon",modifier = Modifier.size(50.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                TruncateFileTitle(file,25)
                IconButton(onClick = {
                        viewModel.removeFile(file)
                }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}