package com.mohd.dev.intertnetChecks

interface NetworkListener {
    fun onNetworkChanged(isConnected: Boolean)
}