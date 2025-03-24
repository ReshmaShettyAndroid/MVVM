package com.example.mvvm.viewModel

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.R
import com.example.mvvm.model.LoginRequest
import com.example.mvvm.model.LoginResponse
import com.example.mvvm.repository.LoginRepository
import com.example.mvvm.utils.ApiStatus
import com.example.mvvm.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext private val context: Context, var loginRepository: LoginRepository, val commonUtils: CommonUtils) : ViewModel() {
    private val _userDetail = MutableStateFlow<ApiStatus<LoginResponse>>(ApiStatus.IdleState)
    val userDetail: StateFlow<ApiStatus<LoginResponse>> = _userDetail

    fun userLogin(loginRequest: LoginRequest) = viewModelScope.launch {
        _userDetail.value = ApiStatus.Loading
        try {
            if(commonUtils.isNetworkConnected(context)) {
                var loginResponse = loginRepository.login(loginRequest)

                _userDetail.value = ApiStatus.Success(loginResponse)
                //Save to preference
                commonUtils.saveIsLogin(true)
            }
            else _userDetail.value = ApiStatus.Failure(context.getString(R.string.connect_to_internet))
        } catch (e: Exception) {
            _userDetail.value = ApiStatus.Failure(e.toString())
        }
    }

    fun getIsUserAlreadyLogin(): Boolean = commonUtils.getIsLogin()

    fun validateEmail(
        loginRequest: LoginRequest,
        emailErrorState: MutableState<String>
    ): Boolean {
        return if (loginRequest.email.isEmpty()) {
            emailErrorState.value = context.getString(R.string.enter_email)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginRequest.email).matches()) {
            emailErrorState.value = context.getString(R.string.enter_valid_email)
            false
        } else {
            emailErrorState.value = ""
            true
        }
    }


    fun validatePassword(
        loginRequest: LoginRequest,
        passwordErrorState: MutableState<String>
    ): Boolean {
        return if (loginRequest.password.length < 4) {
            passwordErrorState.value = context.getString(R.string.enter_valid_password)
            false
        } else {
            passwordErrorState.value = ""
            true
        }
    }

}