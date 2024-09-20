package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.domains.authentication.screens.states.AdventureViewModel
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*

@Composable
fun AdventureScreen(viewModel: AdventureViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzdHJpbmciLCJpYXQiOjE3MjY4NjQ2MTYsImV4cCI6MTcyNzQ2OTQxNn0._Ek2Q2Od63eiHGM8x76sWUT_n6bTD58ibi5x3XyOWWeb7AVLhZ-Mq5LraMp58afj"
        viewModel.getAdventures(token)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)) {

        // Barra de búsqueda personalizada
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0CCAA), RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "", // Estado para el valor de búsqueda
                onValueChange = { /* manejar la búsqueda */ },
                placeholder = { Text("Locales y actividades") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .background(Color(0xFFF0CCAA)), // Fondo de la barra de búsqueda
            )
            Button(
                onClick = { /* Acción de búsqueda */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8A63D)),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Buscar", color = Color.White)
            }
        }

        // Lista de aventuras
        LazyColumn(contentPadding = PaddingValues(
            horizontal = 15.dp,
            vertical = 5.dp
        )) {
            items(viewModel.listaAdventures) { adventure ->
                AdventureCard(adventure, navController)
            }
        }
    }
}

@Composable
fun AdventureCard(adventure: Adventure, navController: NavController) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .clickable {
            navController.navigate("detail_adventure/${adventure.Id}")
        },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0CCAA)),
        border = BorderStroke(1.dp, Color(0xFFE8A63D)) // Borde de color
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(adventure.image)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = "Nombre: ${adventure.nameActivity}",
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "Descripción: ${adventure.description}",
                modifier = Modifier.padding(8.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}
