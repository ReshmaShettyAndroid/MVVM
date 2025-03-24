package com.example.mvvm.viewModel

import androidx.lifecycle.ViewModel
import com.example.mvvm.repository.LoginRepository
import com.example.mvvm.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val commonUtils: CommonUtils) : ViewModel() {

    fun resetLoginStatus(){
        commonUtils.saveIsLogin(false)
    }
}