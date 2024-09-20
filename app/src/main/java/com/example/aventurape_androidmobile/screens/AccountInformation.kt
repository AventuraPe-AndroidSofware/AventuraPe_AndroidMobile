package com.example.aventurape_androidmobile.screens


import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun AccountInformation() {
    val navController = rememberNavController()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)){
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Absolute.Right,
        ){
            Button(
                onClick = {
                //logica backend!!!
                navController.navigate(AccountAdventurerDestination.AccountAdventurer.route)
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xBC765532),
                    contentColor = Color.White
                )
            ) {
                Text(text ="Save", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 20.sp)
            }
        }
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 8.dp),
            horizontalAlignment = Alignment.Start){
            Text(text = "Nombre", modifier=Modifier.width(100.dp),
                color=Color(0xBC765532), fontSize = 17.sp)
            TextField(modifier=Modifier.fillMaxWidth(),
                value = firstName,
                onValueChange = { firstName = it }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 8.dp),
            horizontalAlignment = Alignment.Start){
            Text(text = "Apellido", modifier=Modifier.width(100.dp),
                color=Color(0xBC765532), fontSize = 17.sp)
            TextField(modifier=Modifier.fillMaxWidth(),
                value = lastName,
                onValueChange = { firstName = it }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 8.dp),
            horizontalAlignment = Alignment.Start){
            Text(text = "Email", modifier=Modifier.width(100.dp),
                color=Color(0xBC765532), fontSize = 17.sp)
            TextField(modifier=Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { firstName = it }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 8.dp),
            horizontalAlignment = Alignment.Start){
            Text(text = "Gender", modifier=Modifier.width(100.dp),
                color=Color(0xBC765532), fontSize = 17.sp)
            TextField(modifier=Modifier.fillMaxWidth(),
                value = gender,
                onValueChange = { firstName = it }
            )
        }
    }
}