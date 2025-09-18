package com.mohd.dev.appUtils

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mohd.dev.mvvm.NotesRepo
import com.mohd.dev.room.databases.NotesDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppUtils {

    fun showMessage(context: Context,message: String) {
        val rootView = (context as Activity).findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
    fun currentTime(): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(System.currentTimeMillis()))
    }

    fun getRepository(context: Context): NotesRepo {
        val dao = NotesDatabase.getNotesDatabase(context).noteDao()
        return NotesRepo(dao)
    }

    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}