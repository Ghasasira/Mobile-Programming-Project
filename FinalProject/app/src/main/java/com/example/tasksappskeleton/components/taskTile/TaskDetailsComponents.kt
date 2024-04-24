package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasksappskeleton.Subtask
import com.example.tasksappskeleton.Task
import com.example.tasksappskeleton.controllers.TaskViewModel

@Composable
fun FilesDisplay(navigateFunc:()->Unit, viewModel: TaskViewModel) {
    val fileObserver = viewModel.selectedTask.observeAsState()
    var fileCount = fileObserver.value?.files?.size

    Column(
        modifier = Modifier.fillMaxWidth(),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Attachments(${fileCount})")
//            TextButton(onClick =  navigateFunc ) {
//                Text(text = "Add a new attachment")
//            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (fileObserver.value?.files?.isEmpty() == false) {
            fileObserver.value?.files?.forEach {
                    file -> AttachmentTile(file,viewModel)
            }
        }else{
            Text(text = "No files attached to this task.")
        }
//        AttachmentTile()
//        AttachmentTile()
//        AttachmentTile()
//        AttachmentTile()
    }
}

@Composable
fun TeamDisplay(navigateFunc:()->Unit,viewModel: TaskViewModel) {
    val teamObserver = viewModel.selectedTask.observeAsState()
    var teamSize = teamObserver.value?.contributors?.size
    Column(
        modifier = Modifier.fillMaxWidth(),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Team members($teamSize)")
            TextButton(onClick = navigateFunc ) {
                Text(text = "Add a new member")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (teamObserver.value?.contributors?.isEmpty() == false) {
            teamObserver.value?.contributors?.forEach {
                    contributor -> TeamMemberTile(
                        contributor,
                        viewModel
                        )
            }
        }else{
            Text(text = "No team members yet.")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubtasksDisplay(viewModel: TaskViewModel,task: Task) {
    val subTaskObserver= viewModel.selectedTask.observeAsState()
    val sheetstate = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var numberOfSubTask=subTaskObserver.value?.subtasks?.size

    Column (
        modifier= Modifier.fillMaxWidth(),
        //horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Subtasks($numberOfSubTask)")
            TextButton(onClick = { isSheetOpen=true}) {
                Text(text = "Add a new sub task")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (subTaskObserver.value?.subtasks?.isEmpty() == false) {
            subTaskObserver.value?.subtasks?.forEach {
                subtask ->  SubTaskTile(
                    subtask.status,
                    subtask.subTaskId,
                    subtask.description,
                    subtask.subTaskTitle,
                    viewModel
                )
            }
        }else{
            Text(text = "No sub tasks yet.")
        }


        if (isSheetOpen){
            ModalBottomSheet(
                sheetState=sheetstate,
                onDismissRequest = { isSheetOpen = false }) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .defaultMinSize(minHeight = (screenHeight*0.7f).dp)
                ) {
                    // Title TextField
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description TextField
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth().height(140.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Button to create the subtask
                    Button(
                        onClick = {
                            viewModel.addSubtask(
                                task,
                                Subtask( "ffs",title,description)
                            )
                            isSheetOpen=false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Create Subtask")
                    }
                }
            }
        }
    }
}

@Composable
fun CommentsDisplay() {
    Text(
        text = "To Be Added Soon",
        fontSize = 25.sp,
        color = Color.Red,

    )
}