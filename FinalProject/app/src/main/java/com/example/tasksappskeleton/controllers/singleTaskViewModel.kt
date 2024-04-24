package com.example.tasksappskeleton.controllers


//
//private fun getTasks() {
//    viewModelScope.launch {
//        val fetchedTasks = repo.getAllTasks()
//
//        // Group 1: All tasks
//        val allTasks = fetchedTasks.toList()
//
//        // Group 2: Tasks soon to expire based on end date
//        val soonToExpireTasks = fetchedTasks.filter { task ->
//            // Adjust the threshold as needed, here I'm assuming 1 week from today
//            task.endDate?.isBefore(LocalDate.now().plusDays(7)) ?: false
//        }
//
//        // Group 3: Tasks based on task status
//        val inProgressTasks = fetchedTasks.filter { task ->
//            task.status == TaskStatus.IN_PROGRESS // Adjust as needed
//        }
//        val completedTasks = fetchedTasks.filter { task ->
//            task.status == TaskStatus.COMPLETED // Adjust as needed
//        }
//
//        // Group 4: Expired tasks that weren't completed
//        val expiredTasks = fetchedTasks.filter { task ->
//            task.endDate?.isBefore(LocalDate.now()) ?: false && task.status != TaskStatus.COMPLETED
//        }
//
//        // Now you have your filtered lists, you can do whatever you need with them
//        // For example, you could update your UI with these lists or perform further processing
//
//        // Optionally, you can update your LiveData or State with these filtered lists
//        _allTasks.value = allTasks
//        _soonToExpireTasks.value = soonToExpireTasks
//        _inProgressTasks.value = inProgressTasks
//        _completedTasks.value = completedTasks
//        _expiredTasks.value = expiredTasks
//    }
//}
//
//

//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.tasksappskeleton.Task
////import com.example.tasksappskeleton.allTasks
//
//class SingleTaskViewModel(): ViewModel() {
//    private var _selectedTask = MutableLiveData<Task?>()
//    var selectedTask: LiveData<Task?> = _selectedTask
//
//    fun getSingleTask(taskId: String) {
//        var tasksList= mutableListOf<Task>()
//        tasksList.addAll(allTasks)
//            for (task in tasksList) {
//                if (task.taskId == taskId) {
//                    _selectedTask.value = task
//                    Log.w("me", "Task $taskId found")
//                    Log.w("me", "Task with title ${task.taskTitle} found")
//                    Log.w("me", "Task with title ${selectedTask.value?.taskTitle}")
//                    Log.w("me", "Task with description ${selectedTask.value?.description}")
//                    return
//                }
//            }
//        }
//        // If the task with the specified ID is not found, set selectedTask to null
//        //_selectedTask.value = null
//
//
//}