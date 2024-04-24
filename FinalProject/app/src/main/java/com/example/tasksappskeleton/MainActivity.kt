package com.example.tasksappskeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasksappskeleton.controllers.TaskViewModel
import com.example.tasksappskeleton.pages.CreateNewTask
import com.example.tasksappskeleton.pages.HomePage
import com.example.tasksappskeleton.pages.InviteMembers
import com.example.tasksappskeleton.pages.Login
import com.example.tasksappskeleton.pages.Signup
import com.example.tasksappskeleton.pages.TaskDetails
import com.example.tasksappskeleton.ui.theme.TasksAppSkeletonTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksAppSkeletonTheme {
                SetupNavGraph()
            }
        }
    }

}

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomePage(navController, viewModel)
        }
        composable(
            route = Screen.Login.route
        ) {
            Login(navController, viewModel)
        }
        composable(
            route = Screen.SignUp.route
        ) {
            Signup(navController, viewModel)
        }
        composable(
            route = Screen.TaskDetails.route
        ) {
            TaskDetails(navController, viewModel)
        }
        composable(
            route = Screen.CreateTask.route
        ) {
            CreateNewTask(navController, viewModel)
        }
        composable(
            route = Screen.InviteMembers.route
        ) {
            InviteMembers(navController, viewModel, viewModel.selectedTask.value!!)
        }
    }
}
