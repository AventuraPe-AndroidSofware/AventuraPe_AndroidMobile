import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.HomeAdventurerViewModel
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.models.ProfileE
import com.example.aventurape_androidmobile.ui.theme.cabinFamily


// Define custom colors based on the primary color #765532
private val PrimaryColor = Color(0xFF765532)
private val PrimaryVariantLight = Color(0xFF9B7B5B)
private val PrimaryVariantDark = Color(0xFF523922)
private val BackgroundColor = Color(0xFFF5F0EB)
private val SurfaceColor = Color(0xFFFFFFFF)
private val TextPrimaryColor = Color(0xFF2D1810)

@Composable
fun HomeAdventurerScreen(viewModel: HomeAdventurerViewModel, navController: NavController) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
        viewModel.loadEntrepreneurs()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
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
            // Original header layout
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aventurapelogo),
                    contentDescription = "Logo AventuraPe",
                    modifier = Modifier
                        .size(width = 150.dp, height = 100.dp)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            Box(
                modifier = Modifier
                    .width(450.dp)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Divider(
                    color = PrimaryColor,
                    thickness = 2.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Entrepreneurs Section
            SectionHeader(
                title = "Emprendedores",
                backgroundColor = PrimaryVariantLight
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.entrepreneurs) { entrepreneur ->
                    EntrepreneurCard(entrepreneur = entrepreneur)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Posts Section
            SectionHeader(
                title = "Posts",
                backgroundColor = PrimaryVariantLight
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.activities) { adventure ->
                    AdventureCard(adventure = adventure)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿Estás listo para dejarte llevar?",
                color = TextPrimaryColor,
                fontFamily = cabinFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Button(
                onClick = { /* Acción para el botón */ },
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Text(
                    text = "SORPRÉNDEME",
                    style = MaterialTheme.typography.titleMedium,
                    color = SurfaceColor,
                    fontFamily = cabinFamily
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, backgroundColor: Color) {
    Text(
        text = title,
        fontSize = 28.sp,
        color = PrimaryColor,
        fontWeight = FontWeight.Bold,
        fontFamily = cabinFamily,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, bottom = 3.dp)
    )
}

@Composable
fun AdventureCard(adventure: Adventure) {
    Card(
        modifier = Modifier
            .width(300.dp)
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
fun EntrepreneurCard(entrepreneur: ProfileE) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(percent = 50),
                border = BorderStroke(2.dp, PrimaryColor),
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = rememberImagePainter(entrepreneur.imageUrl),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = entrepreneur.name,
                fontFamily = cabinFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryColor,
                textAlign = TextAlign.Center
            )

            Text(
                text = entrepreneur.email,
                fontFamily = cabinFamily,
                fontSize = 14.sp,
                color = PrimaryVariantDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}