package com.example.aventurape_androidmobile.domains.adventurer.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.FavoriteAdventureViewModel
import androidx.compose.runtime.remember
import kotlinx.coroutines.launch

import androidx.compose.material3.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Brush

@Composable
fun FavoritePublicationsAdventurerScreen(navController: NavController, profileId: Long) {
    val viewModel: FavoriteAdventureViewModel = viewModel()
    val favoritePublications by viewModel.favorites.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    LaunchedEffect(profileId) {
        viewModel.loadFavoriteAdventures(profileId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Tus mejores aventuras",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Content
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.loadFavoriteAdventures(profileId)
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoritePublications) { adventure ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp), // Increased height to accommodate buttons
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFE4C4)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // Image
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(adventure.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = adventure.nameActivity,
                                modifier = Modifier
                                    .size(350.dp, 200.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )

                            // Buttons row at the bottom
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = { navController.navigate("detail_adventure/${adventure.Id}")},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFE8A63D)
                                    ),
                                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                                ) {
                                    Text(text = "Detalle", color = Color.White)
                                }

                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.deleteFavoritePublication(profileId, adventure.Id) { response ->
                                                if (response != null) {
                                                    coroutineScope.launch {
                                                        snackbarHostState.showSnackbar("Se eliminó la publicación de favoritos")
                                                        viewModel.loadFavoriteAdventures(profileId)
                                                    }
                                                } else {
                                                    Log.e("FavoritePublicationsAdventurerScreen", "Error al eliminar la publicación de favoritos")
                                                }
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red
                                    ),
                                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                                ) {
                                    Text(text = "Eliminar", color = Color.White)
                                }
                            }

                            // Gradient and title overlay
                            Box(
                                modifier = Modifier
                                    .height(180.dp)
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(alpha = 0.7f)
                                            )
                                        )
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = adventure.nameActivity,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "S/ ${adventure.cost}",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Snackbar host
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    )
}