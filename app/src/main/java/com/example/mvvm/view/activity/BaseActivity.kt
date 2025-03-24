package com.example.mvvm.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}