package com.mohd.dev

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.mohd.dev.intertnetChecks.NetworkReceiver

class NoteAppEplanet: Application() {

    private var networkReceiver: NetworkReceiver? = null

    override fun onCreate() {
        super.onCreate()

        networkReceiver = NetworkReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver,filter)

        FirebaseApp.initializeApp(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}