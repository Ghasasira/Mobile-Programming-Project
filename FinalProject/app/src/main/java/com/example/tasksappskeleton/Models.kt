package com.example.tasksappskeleton

import java.time.LocalDate
import java.util.Date

data class User(
    val name: String,
    val role: String
){
    constructor():this("", "")
}

data class Subtask(
    val subTaskId: String,
    val subTaskTitle: String,
    val description: String,
    var status: String = "Pending", // e.g., "pending", "completed"

//    val createdBy: String, // Reference to the User who created the subtask
//    val taskId: String // Foreign key referencing the Task this subtask belongs to
){
    constructor() : this("", "", "", "Pending", )
}

data class Task(
    val taskTitle: String,
    val taskId: String,
    val startDate: String, // Consider using a more specific datetime library if needed
    val endDate: String,
    val description: String,
    val status: String= "Pending", // e.g., "pending", "completed", "in progress"
    //val percentageCompleted: Double, // Percentage completion of the task (0.0 to 1.0)
    val subtasks: List<Subtask>, // List of subtasks associated with the task
    val contributors: List<User>, // List of users who contributed to the task
    val files: List<String>, // List of file paths or references (consider using a dedicated file model if needed)
    val creator: String,
){
    constructor() : this("", "", "", "", "", "Pending",  emptyList(), emptyList(), emptyList(),"")
}

data class loginState(
    val firstName: String = "",
    val lastNameSignUp: String = "",
    val userName: String = "",
    val password: String= "",
    val userNameSignUp: String = "",
    val passwordSignUp: String= "",
    val confirmPasswordSignUp: String= "",
    val isLoading : Boolean = false,
    val isSuccesslogin : Boolean = false,
    val signupError : String? = null,
    val loginError : String ? = null
)