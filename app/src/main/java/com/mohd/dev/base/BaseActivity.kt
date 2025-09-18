package com.mohd.dev.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.intertnetChecks.NetworkListener
import com.mohd.dev.intertnetChecks.NetworkReceiver

abstract class BaseActivity: AppCompatActivity(), NetworkListener, View.OnLongClickListener, View.OnClickListener {

    private val networkReceiver = NetworkReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkReceiver.networkListener = this
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        if (isConnected) {
            AppUtils.showMessage(this,"Internet Available")
        } else {
            AppUtils.showMessage(this,"Check your Internet Connection!!!")
        }
    }

    override fun onLongClick(view: View?): Boolean {
        /* TO DO */
        return false
    }

    /* ShowMessage in SnackBar */
//    fun showMessage(message: String) {
//        val rootView = findViewById<View>(android.R.id.content)
//        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
//    }

    override fun onClick(view: View?) {
        /* TO DO */
    }
}