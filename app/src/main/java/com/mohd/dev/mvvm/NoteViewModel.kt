package com.mohd.dev.mvvm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.base.BaseActivity
import com.mohd.dev.firestore.FirestoreHelper
//import com.mohd.dev.firestore.FirestoreHelper
import com.mohd.dev.intertnetChecks.NetworkListener
import com.mohd.dev.intertnetChecks.NetworkReceiver
import com.mohd.dev.room.databases.NotesDatabase
import com.mohd.dev.room.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class NoteViewModel : ViewModel() {
    val firebaseHelper = FirestoreHelper()
    fun repository(context: Context) = AppUtils.getRepository(context)
    fun insertNote(context: Context, note: Note) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository(context).insert(note)
                if (AppUtils.isInternetAvailable(context)) {
                    firebaseHelper.insertOrUpdateNote(note)
                    note.isUploaded = true
                    repository(context).update(note)
                }
            }
            AppUtils.showMessage(context, "Data Inserted UserId: ${note.userId}")
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.d("insertNote: ", exception.message.toString())
            AppUtils.showMessage(context, "Data Insertion Failed: ")
        }
    }

    fun updateNote(context: Context, note: Note) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository(context).update(note)
                if (AppUtils.isInternetAvailable(context)) {
                    note.isUploaded = true
                    firebaseHelper.insertOrUpdateNote(note)
                }
            }
            AppUtils.showMessage(context, "Data Updated UserId: ${note.userId}")
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun deleteNote(context: Context, note: Note) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository(context).delete(note)
                if (AppUtils.isInternetAvailable(context)) {
                    firebaseHelper.deleteNote(note)
                }
            }
            AppUtils.showMessage(context, "Data Deleted Id: ${note.id}")
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun getAll(context: Context): LiveData<List<Note>> {
        return repository(context).getAllNotes()
    }

    fun syncUnuploadedNotes(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val notesToUpload = repository(context).getUnuploadedNotes()
            for (note in notesToUpload) {
                try {
                    firebaseHelper.insertOrUpdateNote(note)
                    note.isUploaded = true
                    repository(context).update(note)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}