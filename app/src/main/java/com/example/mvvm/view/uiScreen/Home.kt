package com.example.mvvm.view.uiScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mvvm.R
import com.example.mvvm.ui.theme.MVVMTheme
import com.example.mvvm.ui.theme.Purple40
import com.example.mvvm.utils.NavRoutes.LOGIN_SCREEN
import com.example.mvvm.viewModel.HomeViewModel

@Composable
fun Home(navController: NavHostController) {
    var homeViewModel: HomeViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment =  Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home_screen),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
        )

        Button(
            onClick = {
                homeViewModel.resetLoginStatus()
                navController.navigate(LOGIN_SCREEN)
            },
            modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Text(
                modifier = Modifier.padding(50.dp, 0.dp, 50.dp, 0.dp),
                text = stringResource(id = R.string.logout), color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    MVVMTheme {
    Home(navController)
}
}
