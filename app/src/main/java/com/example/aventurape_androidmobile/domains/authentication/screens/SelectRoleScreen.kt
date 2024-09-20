package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.authentication.screens.states.SignUpViewModel
import com.example.aventurape_androidmobile.navigation.NavScreen
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

@Composable
fun SelectRoleScreen(viewModel: SignUpViewModel, navController: NavHostController){
    Column(
        modifier= Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_aventurape),
            contentDescription = "Logo image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                viewModel.setRole("ROLE_ADVENTUROUS")
                navController.navigate(NavScreen.signup_screen.name)
            }
        ) {
            Text("Aventurero", fontSize = 22.sp, fontFamily = cabinFamily, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.size(20.dp))
        Text("O", fontSize = 22.sp, fontFamily = cabinFamily, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                viewModel.setRole("ROLE_ADVENTUROUS")
                navController.navigate(NavScreen.signup_screen.name)
            }
        ) {
            Text("Empresario", fontSize = 22.sp, fontFamily = cabinFamily, fontWeight = FontWeight.Bold)
        }
    }
}