package com.example.tasksappskeleton.repo

//import com.example.jetpack.userUI.LOGGIN.loginState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository{
    //var usernameString=
    val currentUser : FirebaseUser? = Firebase.auth.currentUser
    fun hasUser() :Boolean = Firebase.auth.currentUser != null

    fun getUserId() : String? = Firebase.auth.currentUser?.uid.orEmpty()



    private val db = Firebase.firestore

    suspend fun addUserToFirestore(
        firstName: String,
        lastNameSignUp: String,
        userName: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val userMap = hashMapOf(
            "name" to firstName,
            "role" to lastNameSignUp,
            "email" to userName,
            "password" to password
            // Add other user information here
        )

        db.collection("users")
            .add(userMap)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }


    suspend fun CreateUser(
        firstName: String,
        lastNameSignUp: String,
        userName: String,
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO){
        try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            addUserToFirestore(
                firstName,
                lastNameSignUp,
                userName,
                password
            ) { isSuccessful ->
                onComplete.invoke(isSuccessful)
            }
        } catch (e: Exception) {
            onComplete.invoke(false)
        }
    }

    suspend fun loginUser2(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                onComplete.invoke(true)
            }else{
                onComplete.invoke(false)
            }
        }.await()
    }
    fun logoutUser(onComplete: (Boolean) -> Unit) {
        Firebase.auth.signOut()
        onComplete(true)
    }



}