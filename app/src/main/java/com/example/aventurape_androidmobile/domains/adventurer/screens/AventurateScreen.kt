package com.example.aventurape_androidmobile.domains.adventurer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.AventurateViewModel
import com.example.aventurape_androidmobile.ui.theme.cabinFamily

// Define custom colors based on the primary color #765532
private val PrimaryColor = Color(0xFF765532)
private val PrimaryVariantLight = Color(0xFF9B7B5B)
private val PrimaryVariantDark = Color(0xFF523922)
private val BackgroundColor = Color(0xFFF5F0EB)
private val SurfaceColor = Color(0xFFFFFFFF)
private val TextPrimaryColor = Color(0xFF2D1810)

@Composable
fun AventurateScreen(viewModel: AventurateViewModel, navController: NavController) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
        viewModel.loadRandomActivities()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = PrimaryColor
            )
        } else if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {

            SectionHeader(title = "Te recomendamos lo siguiente ...", backgroundColor = PrimaryColor)

            Spacer(modifier = Modifier.height(5.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.activitiesRandom) { adventure ->
                    AdventureCard(adventure = adventure)
                }
            }
        }
    }
}


@Composable
fun AdventureCard(adventure: Adventure) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(adventure.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Price tag overlay
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    color = PrimaryColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "S/. ${adventure.cost}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = adventure.nameActivity,
                    fontFamily = cabinFamily,
                    fontSize = 17.sp,
                    color = TextPrimaryColor,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        DetailItem(
                            label = "Duración",
                            value = "${adventure.timeDuration} horas"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = adventure.description,
                    fontFamily = cabinFamily,
                    fontSize = 14.sp,
                    color = TextPrimaryColor.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontFamily = cabinFamily,
            fontSize = 12.sp,
            color = PrimaryColor,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontFamily = cabinFamily,
            fontSize = 14.sp,
            color = TextPrimaryColor,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun SectionHeader(title: String, backgroundColor: Color) {
    Text(
        text = title,
        fontSize = 22.sp,
        color = PrimaryColor,
        fontWeight = FontWeight.Bold,
        fontFamily = cabinFamily,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 7.dp, bottom = 1.dp)
    )
}