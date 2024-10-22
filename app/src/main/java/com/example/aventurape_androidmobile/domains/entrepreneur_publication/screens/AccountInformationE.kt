package com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.viewModels.ProfileViewModelE
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

@Composable
fun AccountInformationE(navController: NavController, viewModelE: ProfileViewModelE) {
    val state = viewModelE.state
    val scrollState = rememberScrollState()
    val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJob2xhIiwiaWF0IjoxNzI2OTU5NzAwLCJleHAiOjE3Mjc1NjQ1MDB9.Cow3sXgCJVcJ7Bho27rFPU6_XQkKMqeyZ3hTiIPO9eb_5g4qShnZKNeWakdBOLM6"

    LaunchedEffect(viewModelE.state.profileECompletedSucces) {
        if (viewModelE.state.profileECompletedSucces) {
            viewModelE.resetState()
            navController.navigate(NavScreenAdventurer.account_entrepreneur_screen.name)
        }
    }
    Box(modifier = Modifier.verticalScroll(scrollState)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Absolute.Right,
            ) {
                Button(
                    onClick = {
                        Log.d("AccountInformationE", "Save button clicked")
                        viewModelE.viewModelScope.launch {
                            try {
                                viewModelE.completeProfileE(
                                    state.nameEntrepreneurship,
                                    state.addressCity,
                                    state.addressCountry,
                                    state.addressNumber,
                                    state.addressPostalCode,
                                    state.addressStreet,
                                    state.emailAddress
                                )
                                Log.d("AccountInformationE", "Profile completed successfully")
                            } catch (e: Exception) {
                                Log.e("AccountInformationE", "Error completing profile", e)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xBC765532),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Save",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Nombre de la empresa", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 20.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.nameEntrepreneurship,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            it,
                            addressCity = state.addressCity,
                            addressCountry = state.addressCountry,
                            addressNumber = state.addressNumber,
                            addressPostalCode = state.addressPostalCode,
                            addressStreet = state.addressStreet,
                            emailAddress = state.emailAddress
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Direccion", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 20.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.addressCity,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            nameEntrepreneurship=state.nameEntrepreneurship,
                            it,
                            addressCountry = state.addressCountry,
                            addressNumber = state.addressNumber,
                            addressPostalCode = state.addressPostalCode,
                            addressStreet = state.addressStreet,
                            emailAddress = state.emailAddress
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "País", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 20.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.addressCountry,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            nameEntrepreneurship=state.nameEntrepreneurship,
                            addressCity = state.addressCity,
                            it,
                            addressNumber = state.addressNumber,
                            addressPostalCode = state.addressPostalCode,
                            addressStreet = state.addressStreet,
                            emailAddress = state.emailAddress
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Numero de contacto", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 20.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.addressNumber,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            nameEntrepreneurship=state.nameEntrepreneurship,
                            addressCity = state.addressCity,
                            addressCountry = state.addressCountry,
                            it,
                            addressPostalCode = state.addressPostalCode,
                            addressStreet = state.addressStreet,
                            emailAddress = state.emailAddress
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Codigo postal", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 20.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.addressPostalCode,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            nameEntrepreneurship=state.nameEntrepreneurship,
                            addressCity = state.addressCity,
                            addressCountry = state.addressCountry,
                            addressNumber = state.addressNumber,
                            it,
                            addressStreet = state.addressStreet,
                            emailAddress = state.emailAddress
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Direccion", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 20.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.addressStreet,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            nameEntrepreneurship=state.nameEntrepreneurship,
                            addressCity = state.addressCity,
                            addressCountry = state.addressCountry,
                            addressNumber = state.addressNumber,
                            addressPostalCode = state.addressPostalCode,
                            it,
                            emailAddress = state.emailAddress
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Email", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.emailAddress,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            nameEntrepreneurship=state.nameEntrepreneurship,
                            addressCity = state.addressCity,
                            addressCountry = state.addressCountry,
                            addressNumber = state.addressNumber,
                            addressPostalCode = state.addressPostalCode,
                            addressStreet = state.addressStreet,
                            it
                        )
                    }
                )
            }
        }
    }
}
