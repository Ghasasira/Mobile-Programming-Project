package com.example.tasksappskeleton

sealed class Screen (
    val route:String){
    object Home:Screen(route = "homescreen")
    object SignUp:Screen(route="Signup")
    object Login:Screen(route="login")
    object CreateTask:Screen(route = "createTask")
    object InviteMembers:Screen(route="inviteMembers")
    object TaskDetails:Screen(route = "tasksDetails")

}