package com.example.tasksappskeleton.components.taskTile

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import com.example.tasksappskeleton.controllers.FilesViewModel
import com.example.tasksappskeleton.controllers.TaskViewModel

@Composable
fun DocumentPicker(viewModel: TaskViewModel) {
//    val fileViewModel = FilesViewModel()
    val filesState = viewModel.pickedFiles.observeAsState()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris: List<Uri>? ->
        uris?.let {
            viewModel.addPickedFile(it)
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                launcher.launch(arrayOf("application/pdf"))
            }
        ){
            Text("Pick PDF Documents")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if(filesState.value?.size == null){
            Text("No selected files")
        }else{
            Text("${filesState.value?.size} selected")
        }
    }
}
