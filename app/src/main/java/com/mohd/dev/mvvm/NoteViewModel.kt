package com.mohd.dev.mvvm

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.base.BaseActivity
import com.mohd.dev.firestore.FirestoreHelper
import com.mohd.dev.intertnetChecks.NetworkListener
import com.mohd.dev.intertnetChecks.NetworkReceiver
import com.mohd.dev.room.databases.NotesDatabase
import com.mohd.dev.room.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class NoteViewModel : ViewModel() {

    val firebaseHelper = FirestoreHelper()
    private val networkReceiver = NetworkReceiver()

    fun repository(context: Context) = AppUtils.getRepository(context)

    fun networkListener(onAvailable: suspend () -> Unit, onUnAvailable: suspend () -> Unit) {
        networkReceiver.networkListener = object : NetworkListener {
            override fun onNetworkChanged(isConnected: Boolean) {
                viewModelScope.launch(Dispatchers.IO) {
                    if (isConnected) {
                        onAvailable()
                    } else {
                        onUnAvailable()
                    }
                }
            }
        }
    }

    fun insertNote(context: Context,note: Note) {
        try {
            networkListener(
                onAvailable = {
                    repository(context).insert(note)
//                    firebaseHelper.insertNote(note)
                },
                onUnAvailable = {
                    repository(context).insert(note)
                })
            AppUtils.showMessage(context,"Data Inserted UserId: " + note.userId)
        } catch (exception: Exception) {
            exception.printStackTrace()
            AppUtils.showMessage(context,"Data Insertion Failed: ")
        }
    }

    fun updateNote(context: Context,note: Note) {
        try {
            networkListener(onAvailable = {
                repository(context).update(note)
//                firebaseHelper.updateNote(note)
            }, onUnAvailable = {
                repository(context).update(note)
            })
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun deleteNote(context: Context,note: Note) {
        try {
            networkListener(onAvailable = {
                repository(context).delete(note)
//                firebaseHelper.deleteNote(note)
            }, onUnAvailable = {
                repository(context).delete(note)
            })
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun getAll(context: Context): List<Note> {
        return repository(context).allNotes
    }
}