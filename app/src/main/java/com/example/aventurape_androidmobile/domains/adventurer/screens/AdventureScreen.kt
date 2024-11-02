package com.example.aventurape_androidmobile.domains.adventurer.screens

import android.util.Log
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
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.AdventureViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.FavoriteAdventureViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import kotlinx.coroutines.launch
import java.io.Console

@Composable
fun AdventureScreen(viewModel: AdventureViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        viewModel.getAdventures()
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
                value = searchQuery, // Estado para el valor de búsqueda
                onValueChange = { searchQuery = it }, // manejar la búsqueda
                placeholder = { Text("Busca locales y actividades") },
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

        // Lista de aventuras favoritas
        LazyColumn(contentPadding = PaddingValues(
            horizontal = 15.dp,
            vertical = 5.dp
        )
        ) {
            items(viewModel.listaAdventures) { adventure ->
                AdventureCard(adventure, navController, snackbarHostState = SnackbarHostState())
            }
        }
    }

}
@Composable
fun AdventureCard(adventure: Adventure, navController: NavController, snackbarHostState: SnackbarHostState) {
    val coroutineScope = rememberCoroutineScope()

    Log.d("Adventure ID clicked:", "${adventure.Id}")
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .background(Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, Color(0xFFE1AC6E)),
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
        Row {
            Button(
                onClick = { navController.navigate("detail_adventure/${adventure.Id}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8A63D)),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Detalle", color = Color.White)
            }
            val viewModel: FavoriteAdventureViewModel = viewModel()
            Button(
                onClick = {
                    viewModel.addFavoritePublication(publicationId = adventure.Id) { response ->
                        coroutineScope.launch {
                            if (response != null && response.isSuccessful) {
                                Log.d("Lo agregasteee wii.", "${adventure.Id}")

                                // Show success message
                                snackbarHostState.showSnackbar("Added to favorites successfully!")
                            } else {
                                Log.d("Failed to add to favorites.", "Failed to add to favorites.")

                                // Show error message
                                snackbarHostState.showSnackbar("Failed to add to favorites.")
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD78927)),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Favorito", color = Color.White)
            }
        }
    }
}