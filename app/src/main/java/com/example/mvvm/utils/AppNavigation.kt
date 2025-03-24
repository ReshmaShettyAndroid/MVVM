package com.example.mvvm.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvm.utils.NavRoutes.LOGIN_SCREEN
import com.example.mvvm.view.uiScreen.Home
import com.example.mvvm.view.uiScreen.Login

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LOGIN_SCREEN) {
        composable(
            NavRoutes.LOGIN_SCREEN
        ) {
            Login(navController)
        }
        composable(
            NavRoutes.HOME_SCREEN
        ) {
            Home(navController)
        }
    }
}