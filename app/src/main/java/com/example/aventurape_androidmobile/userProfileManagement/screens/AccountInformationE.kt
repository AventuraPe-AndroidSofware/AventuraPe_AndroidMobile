package com.example.aventurape_androidmobile.userProfileManagement.screens

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
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import com.example.aventurape_androidmobile.userProfileManagement.screens.states.ProfileViewModelE
import kotlinx.coroutines.launch

@Composable
fun AccountInformationE(navController: NavController, viewModelE: ProfileViewModelE) {
    val state = viewModelE.state
    val scrollState = rememberScrollState()
    val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJob2xhIiwiaWF0IjoxNzI2OTU5NzAwLCJleHAiOjE3Mjc1NjQ1MDB9.Cow3sXgCJVcJ7Bho27rFPU6_XQkKMqeyZ3hTiIPO9eb_5g4qShnZKNeWakdBOLM6"

    LaunchedEffect(viewModelE.state.profileECompletedSucces) {
        if (viewModelE.state.profileECompletedSucces) {
            viewModelE.resetState()
            navController.navigate(NavScreenAdventurer.AccountE.name)
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
                                    state.email,
                                    state.street,
                                    state.number,
                                    state.city,
                                    state.postalCode,
                                    state.country,
                                    state.name,
                                    token
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
                    text = "Email", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            it,
                            street = state.street,
                            number = state.number,
                            city = state.city,
                            postalCode = state.postalCode,
                            country = state.country,
                            name = state.name
                        )
                    },
                    label = {
                        Text(
                            "Email",
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Normal
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
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.street,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            email = state.email,
                            it,
                            number = state.number,
                            city = state.city,
                            postalCode = state.postalCode,
                            country = state.country,
                            name = state.name
                        )
                    },
                    label = {
                        Text(
                            "Calle",
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Normal
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
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.number,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            email = state.email,
                            street = state.street,
                            it,
                            city = state.city,
                            postalCode = state.postalCode,
                            country = state.country,
                            name = state.name
                        )
                    },
                    label = {
                        Text(
                            "Numero",
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Normal
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
                    text = "Ciudad", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.city,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            email = state.email,
                            street = state.street,
                            number = state.number,
                            it,
                            postalCode = state.postalCode,
                            country = state.country,
                            name = state.name
                        )
                    },
                    label = {
                        Text(
                            "Ciudad",
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Normal
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
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.postalCode,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            email = state.email,
                            street = state.street,
                            number = state.number,
                            city = state.city,
                            it,
                            country = state.country,
                            name = state.name
                        )
                    },
                    label = {
                        Text(
                            "Codigo postal",
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Normal
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
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.country,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            email = state.email,
                            street = state.street,
                            number = state.number,
                            city = state.city,
                            postalCode = state.postalCode,
                            it,
                            name = state.name
                        )
                    },
                    label = {
                        Text(
                            "País",
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Normal
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
                    text = "Nombre de tu emprendimiento", modifier = Modifier.width(100.dp),
                    color = Color(0xBC765532), fontSize = 17.sp
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = state.name,
                    onValueChange = {
                        viewModelE.inputProfileData(
                            email = state.email,
                            street = state.street,
                            number = state.number,
                            city = state.city,
                            postalCode = state.postalCode,
                            country = state.country,
                            it
                        )
                    },
                    label = {
                        Text(
                            "nombre de tu emprendimiento",
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }
                )
            }
        }
    }
}
