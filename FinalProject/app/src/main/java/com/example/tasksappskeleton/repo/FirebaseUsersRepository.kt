package com.example.tasksappskeleton.repo

import android.util.Log
import com.example.tasksappskeleton.Task
import com.example.tasksappskeleton.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    // Function to fetch user data from Firestore based on user ID
    suspend fun getAllUsers(): List<User> {
        val usersList = mutableListOf<User>()

        val collectionRef = db.collection("users")
        val querySnapshot = collectionRef.get().await()  // Use await for suspending function

        for (document in querySnapshot.documents) {
            val retrievedUser = document.toObject(User::class.java)

            if (retrievedUser != null) {
                usersList.add(retrievedUser)
                Log.v("fetch", "Fetch successful but No data fetched")
            } else {
                Log.v("fetch", "Fetch successful but No data fetched")
                // Handle the case where document cannot be converted to Task
                // Log a warning, display an error message, etc.
            }

        }
        return usersList
    }

    suspend fun getUserByEmail(email: String): User? {
        val collectionRef = db.collection("users")
        val querySnapshot = collectionRef.whereEqualTo("email", email).get().await()

        if (querySnapshot.isEmpty) {
            // No user found with the provided email address
            return null
        } else {
            // Assuming there's only one user with a unique email address
            val document = querySnapshot.documents[0]
            return document.toObject(User::class.java)
        }
    }
}