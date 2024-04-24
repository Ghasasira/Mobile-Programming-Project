package com.example.tasksappskeleton.repo

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseStorageRepository {

    private val storageRef = FirebaseStorage.getInstance().getReference();

    // Function to upload a file to Firebase Storage
    suspend fun uploadTheFiles(uris:List<Uri>) :List<String>{
        var files = mutableListOf<String>()
        if(uris.isNotEmpty()){
        uris.forEach { uri ->
            var fileUri = uploadFile(uri)
            files.add(fileUri!!)
        }
        return files
        } else {
            return files
        }
    }

    private suspend fun uploadFile(fileUri: Uri): String? {
        return try {
            // Generate a unique filename for the uploaded file
            val fileName = "file_${UUID.randomUUID()}"

            // Get a reference to the file location in Firebase Storage
            val fileRef = storageRef.child(fileName)

            // Upload the file to Firebase Storage
            fileRef.putFile(fileUri).await()

            // Get the download URL of the uploaded file
            fileRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun uploadPDF(pdfUri:Uri){
        //val storageRef = FirebaseStorage.getInstance().reference
        val pdfRef = storageRef.child("pdfFiles/example.pdf")
        pdfRef.putFile(pdfUri).addOnSuccessListener {
            // File uploaded successfully
            Log.d("Firebase", "PDF file uploaded successfully")
        }.addOnFailureListener {
            // Error occurred while uploading file
            Log.e("Firebase", "Error uploading PDF file", it)
        }
    }

//    fun retrievePDF(){
//        pdfRef.getDownloadUrl().addOnSuccessListener { uri ->
//            // Got the download URL for the PDF file
//            Log.d("Firebase", "PDF file download URL: $uri")
//        }.addOnFailureListener {
//            // Error occurred while retrieving download URL
//            Log.e("Firebase", "Error retrieving PDF file download URL", it)
//        }
//    }


//    fun showPDF(){
//        val pdfView = findViewById(R.id.pdfView)
//        pdfView.fromUri(uri).load()
//    }
}
