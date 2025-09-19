package com.mohd.dev.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.intertnetChecks.NetworkListener
import com.mohd.dev.intertnetChecks.NetworkReceiver

abstract class BaseActivity: AppCompatActivity(), NetworkListener, View.OnClickListener {

    private val receiver = NetworkReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiver.networkListener = this
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        /* TO DO */
    }

    override fun onClick(view: View?) {
        /* TO DO */
    }
}