package com.example.mvvm.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.mvvm.utils.AppConstants.ISLOGIN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CommonUtils @Inject constructor(@ApplicationContext private val context: Context) {

    val sharedPreferences = context.getSharedPreferences("UserPreferences", MODE_PRIVATE)

    // Save Loginstatus(we can also save credential details but for now i am saving state)
    fun saveIsLogin(islogin: Boolean) {
        sharedPreferences.edit().putBoolean(ISLOGIN, islogin).apply()
    }

    // Get Loginstatus
    fun getIsLogin(): Boolean {
        return sharedPreferences.getBoolean(ISLOGIN, false)
    }


    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }


    }