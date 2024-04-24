package com.example.tasksappskeleton.controllers

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksappskeleton.Subtask
import com.example.tasksappskeleton.Task
import com.example.tasksappskeleton.User
import com.example.tasksappskeleton.loginState
import com.example.tasksappskeleton.repo.AuthRepository
import com.example.tasksappskeleton.repo.FirebaseRepository
import com.example.tasksappskeleton.repo.FirebaseStorageRepository
import com.example.tasksappskeleton.repo.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel() : ViewModel() {

    //var context=Context.
    //**************
    private val repository: AuthRepository = AuthRepository()
//    var currentUser = repository.currentUser

    val hasUser: Boolean get() = repository.hasUser()

    var loginUiState by mutableStateOf(loginState())
        private  set

    //***************

    val fileRepo= FirebaseStorageRepository()

    val repo=FirebaseRepository()
    val usersRepo= UserRepository()

    private var _startDate= MutableLiveData<String?>(null)
    var startDate:LiveData<String?> = _startDate

    private var _endDate= MutableLiveData<String?>(null)
    var endDate:LiveData<String?> = _endDate


    // LiveData for list of tasks
    private var _tasks = MutableLiveData<List<Task>>()
    var tasks: LiveData<List<Task>> = _tasks

    private var _soonToExpireTasks = MutableLiveData<List<Task>>()
    var soonToExpireTasks: LiveData<List<Task>> = _soonToExpireTasks

    private var _toDoTasks = MutableLiveData<List<Task>>()
    var toDoTasks: LiveData<List<Task>> = _toDoTasks

    private var _expiredTasks = MutableLiveData<List<Task>>()
    var expiredTasks: LiveData<List<Task>> = _expiredTasks

    private var _completedTasks = MutableLiveData<List<Task>>()
    var completedTasks: LiveData<List<Task>> = _completedTasks

    private var _filteredtasks = MutableLiveData<List<Task>>()

    private val _selectedList = MutableLiveData<String>()
    val selectedList: LiveData<String> = _selectedList

    // LiveData for list of Users
    private var _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users

    private var _currentUser = MutableLiveData<User>()
    var currentUser: LiveData<User> = _currentUser

    private var _selectedTask = MutableLiveData<Task?>()
    var selectedTask: LiveData<Task?> = _selectedTask

    private var _percentageCompleted= MutableLiveData<Double>()
    val percentageCompleted:LiveData<Double> = _percentageCompleted


//    val fileRepo= FirebaseStorageRepository()
//    val fileViewModel=FilesViewModel()

    private val _pickedFiles = MutableLiveData<List<Uri>>()
    val pickedFiles: LiveData<List<Uri>> = _pickedFiles

    private val _uploadingFiles = mutableSetOf<Uri>()
    val uploadingFiles: Set<Uri>
        get() = _uploadingFiles

    private var _loggedInUser by mutableStateOf<User?>(null)
    val loggedInUser: User?
        get() = _loggedInUser

    init {
        getLoggedInUser()
      //  fetchCurrentUser()
        getTasks()
        fetchUsers()
        getLoggedInUser()
        //startFetchingData()
    }

    fun getLoggedInUser(){
        viewModelScope.launch {
            var currentUser = repository.currentUser
            if (currentUser != null) {
                var email = currentUser?.email
                try {
                    _loggedInUser=usersRepo.getUserByEmail(email!!)
                   // _currentUser.value=usersRepo.getUserByEmail(email!!)
                } catch (e: Error) {
                    Log.e("userErorr", "failed to fetch logged in User")
                }
                Log.v("login Details", email.toString())
            } else {
                // No user is signed in
                Log.v("login Details", "No logged in user")
            }
        }
        Log.v("loginShit", "Sweet")
    }
    fun filterTasksByStatus(status: String){
        val tasks= _tasks.value
        _filteredtasks.value= tasks?.filter { it.status.equals(status, ignoreCase = true) }
    }

    private var job: Job? = null

    private fun startFetchingData() {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(3000) // Delay for 5 seconds
                // Call your function here to pull data
                getTasks()
            }
        }
    }

    fun selectList(newList:String){
        _selectedList.value=newList
    }

    fun stopFetchingData() {
        job?.cancel()
    }
    // Function to fetch tasks
    private fun getTasks() {
       // var fetchedTasks= mutableListOf<Task>()
        viewModelScope.launch {
           var fetchedTasks = repo.getAllTasks() //emptyList<Task>()  //repo.getAllTasks()
            // Group 1: All tasks
            if(fetchedTasks.isEmpty()){
                _tasks.value= emptyList()

            }else {
                val allTasks = fetchedTasks //.toList()

                // Group 2: Tasks soon to expire based on end date
            val soonToExpireTasks = fetchedTasks.filter { task ->
                // Adjust the threshold as needed, here I'm assuming 1 week from today
                LocalDate.parse(task.endDate)?.isBefore(LocalDate.now().plusDays(5)) ?: false && task.status.lowercase() != "completed"
            }

                // Group 3: Tasks based on task status
                val toDoTasks = fetchedTasks.filter { task ->
                    task.status.lowercase() == "pending" // Adjust as needed
                }
                val completedTasks = fetchedTasks.filter { task ->
                    task.status.lowercase() == "completed" // Adjust as needed
                }

//            // Group 4: Expired tasks that weren't completed
            val expiredTasks = fetchedTasks.filter { task ->
                LocalDate.parse(task.endDate)?.isBefore(LocalDate.now()) ?: false && task.status.lowercase() != "completed"
            }

                _tasks.value = allTasks
                _soonToExpireTasks.value = soonToExpireTasks
                _toDoTasks.value = toDoTasks
                _completedTasks.value = completedTasks
                _expiredTasks.value = expiredTasks

            }
        }
    }

    fun addPickedFile(uris: List<Uri>) {
//        if (uris.isNotEmpty()) {
//            _pickedFiles.value = uris
//        } else {
//            _pickedFiles.value = emptyList()
//        }
        _pickedFiles.setValue(uris)
    }

    private fun calculatePercentage(task:Task):Double{
        var completeSubtasks=0
        var allSubtasks=0
        if(
            task.subtasks.isEmpty()
        ) {
            return 0.0
        }else{
            task.subtasks.forEach { subtask ->
                if (subtask.status.lowercase() == "completed") {
                    completeSubtasks++
                    //}else{
                }
                allSubtasks++
            }
            return (completeSubtasks.toDouble() / task.subtasks.size.toDouble())
        }
    }

    fun fetchUsers() {
        //var fetchedUsers= mutableListOf<User>()
        viewModelScope.launch {
           var fetchedUsers = usersRepo.getAllUsers()
            _users.setValue(fetchedUsers)
        }
    }


    fun getSingleTask(taskId: String) {

        val tasksList = _tasks.value
        if (tasksList != null) {
            for (task in tasksList) {
                if (task.taskId == taskId) {
                    _selectedTask.value = task
                    _percentageCompleted.value=calculatePercentage(task)
                    return
                }
            }
        }
        // If the task with the specified ID is not found, set selectedTask to null
        _selectedTask.value = null
    }

    private fun reFreshSelectedTask(){
        viewModelScope.launch {
            getSingleTask(_selectedTask.value!!.taskId)
        }

    }


    // Function to add a new task
    fun addTask(task: Task) {

        viewModelScope.launch {
            var filesList= fileRepo.uploadTheFiles(pickedFiles.value!!)
            repo.addTask(task,filesList,_startDate.value!!,_endDate.value!!,loggedInUser!!.name)
            //allTasks.add(task)
            getTasks() // Update tasks list after adding a new one
        }
    }

    // Function to update an existing task
    fun updateSubTaskStatus( subTaskTitle:String) {
        var task=selectedTask.value!!
        viewModelScope.launch {

            repo.updateSubTaskStatus(task, subTaskTitle)
            repo.taskStatusUpdate(task)
            getTasks()
            reFreshSelectedTask()
        }
    }

    // Function to delete a task
    fun deleteTask(task:Task) {
        viewModelScope.launch {
            repo.deleteTask(task)
            getTasks()
            reFreshSelectedTask()
        // Update tasks list after deleting a task
        }
    }

    // Function to add a subtask
    fun addSubtask(task: Task, subtask: Subtask) {
        viewModelScope.launch {
            repo.addSubTaskToTask(task,subtask)
            repo.taskStatusUpdate(task)
            getTasks()
            reFreshSelectedTask()
        }

    }


    // Function to delete a subtask (optional)
    fun deleteSubtask( subtaskTitle: String) {
        var task=selectedTask.value!!
        viewModelScope.launch {
            repo.deleteSubTask(task, subtaskTitle)
            repo.taskStatusUpdate(task)
            getTasks()
            reFreshSelectedTask()
        }

    }

    // Function to add a contributor
    fun addContributor(task:Task , contributors: List<User>) {
        viewModelScope.launch {
            repo.addContributorToTask(task, contributors)
            getTasks()
            reFreshSelectedTask()

        }
    }

    // Function to delete a subtask (optional)
    fun removeContributor(contributor:User) {
        var task=selectedTask.value!!
        viewModelScope.launch {
            repo.deleteContributor(task,contributor)
            getTasks()
            reFreshSelectedTask()
        }
    }

    fun removeFile(file:String) {
        var task=selectedTask.value!!
        viewModelScope.launch {
            repo.deleteFile(task,file)
            getTasks()
            reFreshSelectedTask()

        }
    }

    fun setStartDate(date:String){
        _startDate.value=date
    }

    fun setEndDate(date:String){
        _endDate.value=date
        Log.v("enddate",_endDate.value.toString())
    }

    fun onUserNameChange(userName: String){
        loginUiState = loginUiState.copy(userName = userName)
    }
    fun onPasswordChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }
    fun onUserNameSignUpChange(userName: String){
        loginUiState = loginUiState.copy(userNameSignUp = userName)
    }
    fun onPasswordSignUpChange(password: String){
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }
    fun onConfirmPasswordSignUpChange(password: String){
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    fun onUserlastNameSignUpChange(lastNameSignUp: String){

        loginUiState = loginUiState.copy(lastNameSignUp =lastNameSignUp)
    }
    fun onUserfastNameSignUpChange(firstName: String){
        loginUiState = loginUiState.copy(firstName = firstName)
    }





    private fun validateLoginForm() : Boolean{
        return loginUiState.userName.isNotBlank() && loginUiState.password.isNotBlank()
    }
    private fun validateSignUpForm() : Boolean{
        return loginUiState.userNameSignUp.isNotBlank()
                && loginUiState.passwordSignUp.isNotBlank()
                && loginUiState.confirmPasswordSignUp.isNotBlank()
    }


    suspend fun createUser(context: Context){
        try {
            if(!validateSignUpForm()){
                throw  IllegalArgumentException("Please fill in all fields")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            if(loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp){
                throw IllegalArgumentException("password must be the same")
            }

            loginUiState = loginUiState.copy(signupError = null)
            repository.CreateUser(
                loginUiState.firstName,
                loginUiState.lastNameSignUp,
                loginUiState.userNameSignUp,
                loginUiState.userNameSignUp,
                loginUiState.passwordSignUp
            ){ isSuccessfull ->

                CoroutineScope(Dispatchers.Main).launch {
                    if (isSuccessfull ){
                        Toast.makeText(
                            context, "Successs login",
                            Toast.LENGTH_SHORT
                        ).show()

                        loginUiState = loginUiState.copy( isSuccesslogin = true)
                    } else{
                        Toast.makeText(
                            context, "Failed to login",
                            Toast.LENGTH_SHORT
                        ).show()
                        loginUiState = loginUiState.copy( isSuccesslogin = false)
                    }
                }
            }

        }catch (e: Exception){
            loginUiState = loginUiState.copy( signupError = e.message)
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }


    suspend fun loginUser(context:Context){
        try {
            if(!validateLoginForm()){
                throw  IllegalArgumentException("Please fill in all fields")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.loginUser2(
                loginUiState.userName,
                loginUiState.password
            ){ isSuccessfull ->

                if (isSuccessfull ){
                    Toast.makeText(
                        context, "Successs login",
                        Toast.LENGTH_SHORT
                    ).show()

                    loginUiState = loginUiState.copy( isSuccesslogin = true)
                } else{
                    Toast.makeText(
                        context, "Failed to login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy( isSuccesslogin = false)
                }
            }

        }catch (e: Exception){
            loginUiState = loginUiState.copy( loginError = e.message)
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }
    fun logoutUser(onComplete: (Boolean) -> Unit) {
        repository.logoutUser(onComplete)
        //Firebase.auth.signOut()
        onComplete(true)
    }
    fun resetLoginState() {
        loginUiState = loginState()
    }

}
