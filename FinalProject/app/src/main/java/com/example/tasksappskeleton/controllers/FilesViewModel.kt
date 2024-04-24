package com.example.tasksappskeleton.controllers
//
//import android.net.Uri
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.tasksappskeleton.Task
//import com.example.tasksappskeleton.repo.FirebaseStorageRepository
//
//class FilesViewModel:ViewModel() {
//    val fileRepo= FirebaseStorageRepository()
//    val fileViewModel=FilesViewModel()
//    private val _pickedFiles = MutableLiveData<List<Uri>>()
//    val pickedFiles: LiveData<List<Uri>> = _pickedFiles
//
//    private val _uploadingFiles = mutableSetOf<Uri>()
//    val uploadingFiles: Set<Uri>
//        get() = _uploadingFiles
//
//
//    fun addPickedFile(uris: List<Uri>) {
//
//        _pickedFiles.setValue(uris)
//        Log.v("f",_pickedFiles.value?.size.toString())
//    }
//
//
//    fun removePickedFile(uri: Uri) {
//       // _pickedFiles.remove(uri)
//        // If the file was in the uploading state, remove it from the uploading set as well
//        _uploadingFiles.remove(uri)
//    }
//}