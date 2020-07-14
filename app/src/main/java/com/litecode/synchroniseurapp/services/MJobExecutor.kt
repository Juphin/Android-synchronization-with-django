package com.litecode.synchroniseurapp.services

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask

open class MJobExecutor (private val context: Context) : AsyncTask<Void, Void, String>() {
    internal var str = "Hello coders of the world"

    override fun doInBackground(vararg voids: Void): String {
        return str
    }


    fun checkNetworkConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}