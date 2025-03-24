package com.example.mvvm.view.uiScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mvvm.R
import com.example.mvvm.model.LoginRequest
import com.example.mvvm.model.LoginResponse
import com.example.mvvm.ui.theme.MVVMTheme
import com.example.mvvm.ui.theme.Purple40
import com.example.mvvm.ui.theme.gradient
import com.example.mvvm.utils.ApiStatus
import com.example.mvvm.utils.NavRoutes.HOME_SCREEN
import com.example.mvvm.viewModel.LoginViewModel

@Composable
fun Login(navController: NavHostController) {
    var loginViewModel: LoginViewModel = hiltViewModel()
    //keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    //if user already login then redirect to home screen
    if (loginViewModel.getIsUserAlreadyLogin())
        navController.navigate(HOME_SCREEN)
    else
        Box(modifier = Modifier
            .background(gradient)
            .fillMaxSize(),
            content = {
                //To hold request object
                var loginRequest by remember { mutableStateOf(LoginRequest("", "")) }
                var isButtonClicked by remember { mutableStateOf(false) }

                //To hold Error state
                var emailErrorState = remember { mutableStateOf("") }
                var passwordErrorState = remember { mutableStateOf("") }


                LaunchedEffect(isButtonClicked, loginRequest) {
                    if (isButtonClicked) {
                        var isEmailValid =
                            loginViewModel.validateEmail(loginRequest, emailErrorState)
                        var isPasswordValid =
                            loginViewModel.validatePassword(loginRequest, passwordErrorState)

                        //If all fields are valid then call login api
                        if (isEmailValid && isPasswordValid)
                            loginViewModel.userLogin(loginRequest)
                    }
                    isButtonClicked = false
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
                {
                    Text(
                        text = stringResource(id = R.string.login_txt),
                        color = Color.Black,
                        modifier = Modifier
                            .padding(50.dp)
                            .wrapContentHeight()
                            .align(Alignment.CenterHorizontally),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally)
                    ) {

                        OutlinedTextField(
                            value = loginRequest.email,
                            onValueChange = {
                                loginRequest = loginRequest.copy(email = it)
                                loginViewModel.validateEmail(loginRequest, emailErrorState)
                            },
                            label = { Text(text = stringResource(id = R.string.enter_email)) },
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .align(Alignment.CenterHorizontally),
                            isError = emailErrorState.value.isNotEmpty(),
                        )
                        showErrorMsg(emailErrorState.value)

                        OutlinedTextField(
                            value = loginRequest.password,
                            onValueChange = {
                                loginRequest =
                                    loginRequest.copy(password = it)
                                loginViewModel.validatePassword(loginRequest, passwordErrorState)
                            },
                            label = { Text(text = stringResource(id = R.string.enter_password)) },
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .align(Alignment.CenterHorizontally),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                            isError = passwordErrorState.value.isNotEmpty(),
                        )
                        showErrorMsg(passwordErrorState.value)

                        Button(
                            onClick = {
                                keyboardController?.hide()
                                isButtonClicked = true
                            },
                            modifier = Modifier
                                .padding(50.dp)
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(200.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
                        ) {
                            Text(
                                modifier = Modifier.padding(50.dp, 0.dp, 50.dp, 0.dp),
                                text = stringResource(id = R.string.login_txt), color = Color.White
                            )
                        }
                    }
                }

                //Api call management
                handleLoginApiCall(
                    Modifier.align(Alignment.Center),
                    loginViewModel,
                    navController
                )

            })
}


@Composable
fun showErrorMsg(errorState: String) {
    if (errorState.isNotEmpty()) {
        Text(
            text = errorState,
            color = Color.Red,
            modifier = Modifier.padding(start = 25.dp)
        )
    }
}


@Composable
fun handleLoginApiCall(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    navController: NavHostController
) {
    val apiStatus by loginViewModel.userDetail.collectAsState()
    //Api call management
    when (apiStatus) {
        is ApiStatus.Loading -> {
            CircularProgressIndicator(modifier = modifier)
        }

        is ApiStatus.Success -> {
            var a = (apiStatus as ApiStatus.Success<LoginResponse>).data
            Log.d("TAG", "TAG: Response:-" + a.toString())
            navController.navigate(HOME_SCREEN)
        }

        is ApiStatus.Failure -> {
            ShowAlertDialog((apiStatus as ApiStatus.Failure).msg, modifier)
        }

        else -> {}
    }
}

@Composable
fun ShowAlertDialog(txtmsg: String, modifier: Modifier) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Set the state to false to dismiss the dialog
                showDialog = false
            },
            title = { Text(text = "Error") },
            text = { Text(txtmsg) },
            confirmButton = {
                Button(modifier = modifier,
                    onClick = {
                        // Dismiss the dialog on positive button click
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val navController = rememberNavController()
    MVVMTheme {
        Login(navController)
    }
}
