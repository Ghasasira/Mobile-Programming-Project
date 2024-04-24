package com.example.tasksappskeleton.repo

import android.util.Log
import com.example.tasksappskeleton.Subtask
import com.example.tasksappskeleton.Task
import com.example.tasksappskeleton.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.Date

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()

    // Function to add data to Firestore
    fun addTask(taskData: Task, files:List<String>, startDate: String, endDate:String,owner:String) {

        val taskMap= hashMapOf(
            "taskTitle" to taskData.taskTitle,
            "taskId" to taskData.taskId,
            "startDate" to startDate,
            "endDate" to endDate,
            "description" to taskData.description,
            "status" to taskData.status,
            "subtasks" to taskData.subtasks,
            "contributors" to taskData.contributors,
            "files" to files.ifEmpty { emptyList<String>() },
            "creator" to owner

        )

        db.collection("tasks").document().set(taskMap)
    }

    // Functions to retrieve data from Firestore
    suspend fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>()
        try {
            val collectionRef = db.collection("tasks")
                //.whereArrayContains("contributors", user.name)
            Log.v("fetch", "step 1")
            val querySnapshot = collectionRef.get().await()  // Use await for suspending function
            Log.v("fetch", "Fetch part 1 successful")

            if (querySnapshot.isEmpty) {
                Log.v("fetch", "No tasks found")
                return tasksList // Return empty list if no data fetched
            }

            for (document in querySnapshot.documents) {
                try {
                    val retrievedTask = document.toObject(Task::class.java)
                    if (retrievedTask != null) {
                        tasksList.add(retrievedTask)
                        Log.v("fetch", "Fetch successful")
                    } else {
                        Log.v("fetch", "Failed to convert document to Task")
                    }
                } catch (e: Error) {
                    Log.v("fetch", e.toString())
                    Log.v("fetch", "Error fetching tasks") // More descriptive error message
                    //return tasksList
                }
            }

            return tasksList
        }catch (e:Error){
            return tasksList
            Log.v("fetch", "tasks non existant")
        }
    }

    suspend fun deleteTask(task: Task) {
        val collectionRef = db.collection("tasks")
        try {
            val querySnapshot = collectionRef
                .whereEqualTo("taskTitle", task.taskTitle)
                .whereEqualTo("description", task.description)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                // Delete each document that matches the criteria
                collectionRef.document(document.id).delete().await()
                Log.d("FirebaseRepository", "Document deleted successfully")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error deleting document", e)
        }
    }


    suspend fun addSubTaskToTask(task:Task, subTaskData: Subtask) {
        val collectionRef = db.collection("tasks")
        try {
            // Query the tasks collection to find the document with matching title and description
            val querySnapshot = collectionRef
                .whereEqualTo("taskTitle", task.taskTitle)
                .whereEqualTo("description", task.description)
                .get()
                .await()

            // Check if there is a document with the given title and description
            if (!querySnapshot.isEmpty) {
                // Get the reference to the document
                val documentSnapshot = querySnapshot.documents.first()
                val documentId = documentSnapshot.id

                // Update the document with the new task data
                collectionRef.document(documentId)
                    .update("subtasks", FieldValue.arrayUnion(subTaskData))
                    .addOnSuccessListener {
                        Log.d("FirebaseRepository", "Task added to document successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseRepository", "Error adding task to document", e)
                    }
                    .await()
            } else {
                Log.e("FirebaseRepository", "Document not found")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Exception while adding task to document", e)
        }
    }

    suspend fun addContributorToTask(task:Task, contributorData: List<User>) {
        val collectionRef = db.collection("tasks")
        try {
            // Query the tasks collection to find the document with matching title and description
            val querySnapshot = collectionRef
                .whereEqualTo("taskTitle", task.taskTitle)
                .whereEqualTo("description", task.description)
                .get()
                .await()

            // Check if there is a document with the given title and description
            if (!querySnapshot.isEmpty) {
                // Get the reference to the document
                val documentSnapshot = querySnapshot.documents.first()
                val documentId = documentSnapshot.id

                contributorData.forEach { contributor ->
                // Update the document with the new task data
                collectionRef.document(documentId)
                    .update("contributors", FieldValue.arrayUnion(contributor))
                    .addOnSuccessListener {
                        Log.d("FirebaseRepository", "Contributor added to document successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseRepository", "Error adding contributor to document", e)
                    }
                    .await()
            }
            } else {
                Log.e("FirebaseRepository", "Document not found")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Exception while adding task to document", e)
        }
    }

    suspend fun updateSubTaskStatus(task: Task, subtaskTitle: String) {
        val collectionRef = db.collection("tasks")

        try {
            // Query the tasks collection to find the document with matching title and description
            val querySnapshot = collectionRef
                .whereEqualTo("taskTitle", task.taskTitle)
                .whereEqualTo("description", task.description)
                .get()
                .await()

            // Check if there is a document with the given title and description
            if (!querySnapshot.isEmpty) {
                // Get the reference to the document
                val documentSnapshot = querySnapshot.documents.first()
                val documentId = documentSnapshot.id
                val taskRef = collectionRef.document(documentId)

                try {
                    // Update the subtask within the task document using a transaction
                    db.runTransaction { transaction ->
                        val taskSnapshot = transaction.get(taskRef)
                        val taskData = taskSnapshot.data

                        // Check if taskData is not null
                        taskData?.let { data ->
                            // Find the subtask with matching title within the subtasks array
                            val subtasks = (data["subtasks"] as? List<Map<String, Any>>)?.map { subtaskMap ->
                                Subtask(
                                    subtaskMap["subTaskId"] as String,
                                    subtaskMap["subTaskTitle"] as String,
                                    subtaskMap["description"] as String,
                                    subtaskMap["status"] as String
                                )
                            }?.toMutableList() ?: mutableListOf()

                            val subtaskIndex = subtasks.indexOfFirst { it.subTaskTitle == subtaskTitle }

                            if (subtaskIndex != -1) {
                                subtasks[subtaskIndex].status = "completed" // Update status within subtask
                                transaction.update(taskRef, "subtasks", subtasks)  // Update the subtasks array
                            } else {
                                Log.w("FirebaseRepository", "Subtask with title '$subtaskTitle' not found")
                            }
                        } ?: Log.e("FirebaseRepository", "Task data is null")

                    }.addOnFailureListener { e ->
                        Log.e("FirebaseRepository", "Transaction failed: ", e)
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseRepository", "Error updating subtask status", e)
                }
            } else {
                Log.e("FirebaseRepository", "Document not found")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Exception while querying Firestore", e)
        }
    }

    suspend fun deleteSubTask(task: Task, subtaskTitle: String) {
        val collectionRef = db.collection("tasks")

        try {
            // Query the tasks collection to find the document with matching title and description
            val querySnapshot = collectionRef
                .whereEqualTo("taskTitle", task.taskTitle)
                .whereEqualTo("description", task.description)
                .get()
                .await()

            // Check if there is a document with the given title and description
            if (!querySnapshot.isEmpty) {
                // Get the reference to the document
                val documentSnapshot = querySnapshot.documents.first()
                val documentId = documentSnapshot.id
                val taskRef = collectionRef.document(documentId)

                try {
                    // Update the subtask within the task document using a transaction
                    db.runTransaction { transaction ->
                        val taskSnapshot = transaction.get(taskRef)
                        val taskData = taskSnapshot.data

                        // Check if taskData is not null
                        taskData?.let { data ->
                            // Find the subtask with matching title within the subtasks array
                            val subtasks = (data["subtasks"] as? List<Map<String, Any>>)?.map { subtaskMap ->
                                Subtask(
                                    subtaskMap["subTaskId"] as String,
                                    subtaskMap["subTaskTitle"] as String,
                                    subtaskMap["description"] as String,
                                    subtaskMap["status"] as String
                                )
                            }?.toMutableList() ?: mutableListOf()

                            val subtaskIndex = subtasks.indexOfFirst { it.subTaskTitle == subtaskTitle }

                            if (subtaskIndex != -1) {
                                subtasks.removeAt(subtaskIndex) // Remove the subtask from the list
                                transaction.update(taskRef, "subtasks", subtasks)  // Update the subtasks array
                            } else {
                                Log.w("FirebaseRepository", "Subtask with title '$subtaskTitle' not found")
                            }
                        } ?: Log.e("FirebaseRepository", "Task data is null")

                    }.addOnFailureListener { e ->
                        Log.e("FirebaseRepository", "Transaction failed: ", e)
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseRepository", "Error deleting subtask", e)
                }
            } else {
                Log.e("FirebaseRepository", "Document not found")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Exception while querying Firestore", e)
        }
    }

    suspend fun deleteContributor(task: Task, contributor:User) {
        val collectionRef = db.collection("tasks")

        try {
            // Query the tasks collection to find the document with matching title and description
            val querySnapshot = collectionRef
                .whereEqualTo("taskTitle", task.taskTitle)
                .whereEqualTo("description", task.description)
                .get()
                .await()

            // Check if there is a document with the given title and description
            if (!querySnapshot.isEmpty) {
                // Get the reference to the document
                val documentSnapshot = querySnapshot.documents.first()
                val documentId = documentSnapshot.id
                val taskRef = collectionRef.document(documentId)

                try {
                    // Update the contributors within the task document using a transaction
                    db.runTransaction { transaction ->
                        val taskSnapshot = transaction.get(taskRef)
                        val taskData = taskSnapshot.data

                        // Check if taskData is not null
                        taskData?.let { data ->
                            // Find the contributor with matching name and role within the contributors array
                            val contributors = (data["contributors"] as? List<Map<String, Any>>)?.map { contributorMap ->
                                User(
                                    contributorMap["name"] as String,
                                    contributorMap["role"] as String
                                )
                            }?.toMutableList() ?: mutableListOf()

                            val contributorIndex = contributors.indexOfFirst {
                                it.name == contributor.name && it.role == contributor.role
                            }

                            if (contributorIndex != -1) {
                                contributors.removeAt(contributorIndex) // Remove the contributor from the list
                                transaction.update(taskRef, "contributors", contributors)  // Update the contributors array
                            } else {
                                Log.w("FirebaseRepository", "Contributor with name ${contributor.name} and role ${contributor.role} not found")
                            }
                        } ?: Log.e("FirebaseRepository", "Task data is null")

                    }.addOnFailureListener { e ->
                        Log.e("FirebaseRepository", "Transaction failed: ", e)
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseRepository", "Error deleting contributor", e)
                }
            } else {
                Log.e("FirebaseRepository", "Document not found")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Exception while querying Firestore", e)
        }
    }

    suspend fun deleteFile(task: Task, fileName: String) {
        val collectionRef = db.collection("tasks")

        try {
            // Query the tasks collection to find the document with matching title and description
            val querySnapshot = collectionRef
                .whereEqualTo("taskTitle", task.taskTitle)
                .whereEqualTo("description", task.description)
                .get()
                .await()

            // Check if there is a document with the given title and description
            if (!querySnapshot.isEmpty) {
                // Get the reference to the document
                val documentSnapshot = querySnapshot.documents.first()
                val documentId = documentSnapshot.id
                val taskRef = collectionRef.document(documentId)

                try {
                    // Update the files within the task document using a transaction
                    db.runTransaction { transaction ->
                        val taskSnapshot = transaction.get(taskRef)
                        val taskData = taskSnapshot.data

                        // Check if taskData is not null
                        taskData?.let { data ->
                            // Find the files array
                            val files = (data["files"] as? MutableList<String>) ?: mutableListOf()

                            // Remove the specified file from the list
                            files.remove(fileName)

                            // Update the files array in Firestore
                            transaction.update(taskRef, "files", files)
                        } ?: Log.e("FirebaseRepository", "Task data is null")

                    }.addOnFailureListener { e ->
                        Log.e("FirebaseRepository", "Transaction failed: ", e)
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseRepository", "Error deleting file", e)
                }
            } else {
                Log.e("FirebaseRepository", "Document not found")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Exception while querying Firestore", e)
        }
    }
//????????????????????????????????
suspend fun taskStatusUpdate(task: Task) {
    val collectionRef = db.collection("tasks")
    try {
        // Query the tasks collection to find the document with matching title and description
        val querySnapshot = collectionRef
            .whereEqualTo("taskTitle", task.taskTitle)
            .whereEqualTo("description", task.description)
            .get()
            .await()

        // Check if there is a document with the given title and description
        if (!querySnapshot.isEmpty) {
            // Get the reference to the document
            val documentSnapshot = querySnapshot.documents.first()
            val documentId = documentSnapshot.id

            // Fetch the task document from Firestore
            val taskDocument = collectionRef.document(documentId).get().await()

            // Get the list of subtasks from the task document
            val subtasks = taskDocument.toObject(Task::class.java)?.subtasks ?: emptyList()

            // Determine the task status based on the subtask statuses
            val newStatus = when {
                subtasks.all { it.status == "completed" } -> "Completed"
                subtasks.all { it.status != "completed" } -> "Pending"
                else -> "In Progress"
            }

            try {
                // Update the document with the new status value
                collectionRef.document(documentId)
                    .update("status", newStatus)
                    .addOnSuccessListener {
                        Log.d("FirebaseRepository", "Status updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseRepository", "Error updating status", e)
                    }
                    .await() // Wait for the update operation to complete
            } catch (e: Exception) {
                Log.e("FirebaseRepository", "Error updating status", e)
            }
        } else {
            Log.e("FirebaseRepository", "Document not found")
        }
    } catch (e: Exception) {
        Log.e("FirebaseRepository", "Exception while adding task to document", e)
    }
}

}
