package com.example.tasksappskeleton.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasksappskeleton.Screen
import com.example.tasksappskeleton.Task
import com.example.tasksappskeleton.components.taskTile.DocumentPicker
import com.example.tasksappskeleton.components.taskTile.EndDatePickerDialog
import com.example.tasksappskeleton.components.taskTile.StartDatePickerDialog
//import com.example.tasksappskeleton.controllers.FilesViewModel
import com.example.tasksappskeleton.controllers.TaskViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTask(navController: NavController, viewModel: TaskViewModel) {
    val startDateStatus = viewModel.startDate.observeAsState()
    val endDateStatus = viewModel.endDate.observeAsState()
    // State for task details
//    val fileViewModel = FilesViewModel()
//    val fileRepo = FirebaseStorageRepository()
    val titleState = remember { mutableStateOf("") }
    val startDateState = remember { mutableStateOf(LocalDate.now()) }
    val endDateState = remember { mutableStateOf(LocalDate.now()) }
    val descriptionState = remember { mutableStateOf("") }
    val attachedFilesState = remember { mutableStateOf(listOf<String>()) }
    val screenHeight= LocalConfiguration.current.screenHeightDp

    var openStartDatePickerDialog = remember { mutableStateOf(false) }
    var openEndDatePickerDialog = remember { mutableStateOf(false) }
    //val selectedEndDate = remember { mutableStateOf(LocalDate.now()) }  // Initial date

    var files = mutableListOf<String>()

    fun formatSelectedDay(selectedDate: Date?): String {
        selectedDate ?: return "" // Return empty string if selectedDate is null

        val dateFormat = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())
        return dateFormat.format(selectedDate)
    }

    // Function to handle creating the task
    fun createTask():Task {
        return Task(
            titleState.value,
            "${titleState.value}_${Random.nextInt(50).toString()}",
            "",
            "",
            descriptionState.value,
            "Pending",
            mutableListOf(),
            mutableListOf(),
            files,
            ""

        )
    }
    //val date = remember { mutableStateOf(LocalDate.now())}
    //val isOpen = remember { mutableStateOf(false)}
    //var startDate= formatSelectedDay(startDateStatus.value)
   // var endDate= formatSelectedDay(endDateStatus.value)
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,

                    ),
                title = { Text(text = "Add New Task", color = Color.White)},
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
    ) { paddingValues-> Box(
        modifier = Modifier.padding(paddingValues)
    ) {
            Column(
                modifier = Modifier
                    .height(screenHeight.dp)
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp), //top = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Input fields for task details
                OutlinedTextField(
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { /* Handle next action */ }
                    )
                )
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Column(
//                        verticalArrangement =Arrangement.SpaceAround,
//                        modifier = Modifier
//                            .width(200.dp)
//                            .height(80.dp)
//                    ) {
//                        Text(
//                            text= startDate,
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Button(onClick = { openStartDatePickerDialog.value=true  }) {
//                            Text("Set Start Date")
//                        }
//                    }
//                    Column(
//                        verticalArrangement =Arrangement.SpaceAround,
//                        modifier = Modifier
//                            .width(200.dp)
//                            .height(80.dp)
//                    ) {
//                        Text(
//                            text = endDate,
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Button(onClick = { openEndDatePickerDialog.value=true }) {
//                            Text("Set End Date")
//                        }
//                    }
//                }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        //modifier = Modifier.width(150.dp)
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = startDateState.value.format(DateTimeFormatter.ISO_DATE),
                            label = { Text("Start Date") },
                            onValueChange = {})
                        IconButton(
                            onClick = { openStartDatePickerDialog.value = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Calendar"
                            )
                        }
                    }
                Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        //modifier = Modifier.width(120.dp)
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = endDateState.value.format(DateTimeFormatter.ISO_DATE),
                            label = { Text("End Date") },
                            onValueChange = {})
                        IconButton(
                            onClick = { openEndDatePickerDialog.value = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Calendar"
                            )
                        }
                    }



                if(
                    openStartDatePickerDialog.value
                ){
                    StartDatePickerDialog(
                        viewModel,
                        onAccept = {
                            openStartDatePickerDialog.value = false // close dialog

                            if (it != null) { // Set the date
                                startDateState.value = Instant
                                    .ofEpochMilli(it)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                            }
                            viewModel.setStartDate(startDateState.value.toString())
                        },
                        onCancel = {
                            openStartDatePickerDialog.value = false //close dialog
                        }
                    )
                }
                if(
                    openEndDatePickerDialog.value
                ){
                    EndDatePickerDialog(
                        viewModel,
                        onAccept = {
                            openEndDatePickerDialog.value = false // close dialog

                            if (it != null) { // Set the date
                                endDateState.value = Instant
                                    .ofEpochMilli(it)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                            }
                            Log.v("endDate",endDateState.value.toString())
                            viewModel.setEndDate(endDateState.value.toString())
                        },
                        onCancel = {
                            openEndDatePickerDialog.value = false //close dialog
                        }
                    )
               }
//

                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = descriptionState.value,
                    onValueChange = { descriptionState.value = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle done action */ }
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                DocumentPicker(viewModel)
                //{
                    //fileRepo.uploadTheFiles(it)
                    //it
              //  }

                Spacer(modifier = Modifier.height(16.dp))

                // Button to create task
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    onClick = {
                        viewModel.addTask(createTask())
                        //fileRepo.uploadTheFiles(fileViewModel.pickedFiles.value!!)
                        navController.navigate(Screen.Home.route)
                              },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Create Task")
                }
            }
        }
    }
}

