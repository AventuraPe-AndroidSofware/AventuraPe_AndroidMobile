package com.example.aventurape_androidmobile.domains.adventurer.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.AdventureViewModel
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.FavoriteAdventureViewModel
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

// Define color scheme based on 765532FF
private val PrimaryColor = Color(0xFF765532)
private val PrimaryVariantLight = Color(0xFF9B7B5B)
private val PrimaryVariantDark = Color(0xFF523923)
private val SurfaceColor = Color(0xFFF5EDE4)
private val TextPrimaryColor = Color(0xFF2D1810)
private val TextSecondaryColor = Color(0xFF5D4B3E)

@Composable
fun AdventureScreen(viewModel: AdventureViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        viewModel.getAdventures()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceColor)
            .padding(16.dp)
    ) {
        // Search bar with enhanced design
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(12.dp)),
            placeholder = {
                Text(
                    "Busca locales y actividades",
                    color = TextSecondaryColor
                )
            },
            trailingIcon = {
                IconButton(onClick = { /* Search functionality */ }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = PrimaryColor
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = PrimaryVariantLight,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        // Adventures list
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.listaAdventures) { adventure ->
                AdventureCard(adventure, navController, SnackbarHostState())
            }
        }
    }
}

@Composable
fun AdventureCard(adventure: Adventure, navController: NavController, snackbarHostState: SnackbarHostState) {
    val coroutineScope = rememberCoroutineScope()
    val favoriteViewModel: FavoriteAdventureViewModel = viewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            // Image section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(adventure.image)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Content section
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = adventure.nameActivity,
                    fontFamily = cabinFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = adventure.description,
                    fontFamily = cabinFamily,
                    fontSize = 16.sp,
                    color = TextSecondaryColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Buttons row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.navigate("detail_adventure/${adventure.Id}") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Ver Detalles",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            favoriteViewModel.addFavoritePublication(publicationId = adventure.Id) { response ->
                                coroutineScope.launch {
                                    if (response != null && response.isSuccessful) {
                                        snackbarHostState.showSnackbar("¡Añadido a favoritos!")
                                    } else {
                                        snackbarHostState.showSnackbar("Error al añadir a favoritos")
                                    }
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = "Añadir a favoritos",
                            tint = PrimaryColor
                        )
                    }
                }
            }
        }
    }
}