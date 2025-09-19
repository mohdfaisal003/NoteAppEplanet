package com.mohd.dev

import android.app.Activity
import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.intertnetChecks.NetworkListener
//import com.google.firebase.Firebase
//import com.google.firebase.FirebaseApp
import com.mohd.dev.intertnetChecks.NetworkReceiver

class NoteAppEplanet: Application() {

    private var networkReceiver = NetworkReceiver()

    override fun onCreate() {
        super.onCreate()

        networkReceiver.networkListener = object : NetworkListener {
            override fun onNetworkChanged(isConnected: Boolean) {
                if (isConnected) {
                    Toast.makeText(this@NoteAppEplanet,"Internet Available", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@NoteAppEplanet,"Check your Internet Connection!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)

        FirebaseApp.initializeApp(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}