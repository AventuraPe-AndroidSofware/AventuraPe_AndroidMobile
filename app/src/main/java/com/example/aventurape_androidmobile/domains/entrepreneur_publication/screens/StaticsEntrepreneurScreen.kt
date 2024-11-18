import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import com.example.aventurape_androidmobile.utils.models.PublicationByOrderResponse

val PrimaryBrown = Color(0xFF765532)
val LightBrown = Color(0xFF9B7B5B)
val DarkBrown = Color(0xFF513A23)
val BackgroundBrown = Color(0xFFF5E6D3)
val TextBrown = Color(0xFF2D1810)

@Composable
fun StaticsEntrepreneurScreen(context: Context) {
    val statisticsViewModel: StatisticsViewModel = viewModel()
    val userAdventures = statisticsViewModel.userAdventures.observeAsState(emptyList())
    val topAdventures = statisticsViewModel.topAdventures.observeAsState(emptyList())
    val topRatedPublications = statisticsViewModel.topRatedPublications.observeAsState(emptyList())
    val entrepreneurId = PreferenceManager.getUserId(context)
    var selectedTab by remember { mutableStateOf("Mis publicaciones") }
    var isLoading by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    val tabs = listOf("Mis publicaciones", "Más comentados", "Mejores puntuaciones")

    LaunchedEffect(selectedTab) {
        isLoading = true
        when (selectedTab) {
            "Mis publicaciones" -> statisticsViewModel.getUserAdventures(entrepreneurId)
            "Más comentados" -> statisticsViewModel.getTop5AdventuresByComments()
            "Mejores puntuaciones" -> statisticsViewModel.getFavoritePublicationsByProfileIdOrderedByRating(entrepreneurId)
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBrown)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryBrown)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Estadísticas",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = cabinFamily,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        when (selectedTab) {
                            "Mis publicaciones" -> statisticsViewModel.getUserAdventures(entrepreneurId)
                            "Más comentados" -> statisticsViewModel.getTop5AdventuresByComments()
                            "Mejores puntuaciones" -> statisticsViewModel.getFavoritePublicationsByProfileIdOrderedByRating(entrepreneurId)
                        }
                    },
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_refresh),
                        contentDescription = "Refresh",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Tab Selector
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBrown)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .clickable { expanded = true }
                    .padding(12.dp)
            ) {
                Text(
                    text = selectedTab,
                    color = TextBrown,
                    fontFamily = cabinFamily,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = TextBrown
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                tabs.forEach { tab ->
                    DropdownMenuItem(
                        onClick = {
                            selectedTab = tab
                            expanded = false
                        }
                    ) {
                        Text(
                            text = tab,
                            color = TextBrown,
                            fontFamily = cabinFamily
                        )
                    }
                }
            }
        }

        if (isLoading || statisticsViewModel.isLoadingTopAdventures) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundBrown)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryBrown
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = when (selectedTab) {
                            "Mis publicaciones" -> "Mis Aventuras Publicadas"
                            "Más comentados" -> "Top 5 Aventuras Más Comentadas"
                            else -> "Aventuras Mejor Calificadas"
                        },
                        fontSize = 24.sp,
                        color = TextBrown,
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                when (selectedTab) {
                    "Mis publicaciones" -> {
                        items(userAdventures.value) { adventure ->
                            AdventureCard(adventure, statisticsViewModel)
                        }
                    }
                    "Más comentados" -> {
                        items(topAdventures.value) { adventure ->
                            AdventureCard(adventure, statisticsViewModel)
                        }
                    }
                    "Mejores puntuaciones" -> {
                        items(topRatedPublications.value) { publication ->
                            RatedPublicationCard(publication)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdventureCard(adventure: Adventure, viewModel: StatisticsViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(adventure.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = adventure.nameActivity,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBrown,
                    fontFamily = cabinFamily
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = adventure.description,
                    fontSize = 16.sp,
                    color = TextBrown.copy(alpha = 0.8f),
                    fontFamily = cabinFamily
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .background(LightBrown.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = PrimaryBrown,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Comentarios: ${viewModel.countCommentsForAdventure(adventure.Id)}",
                        fontSize = 14.sp,
                        color = TextBrown,
                        fontFamily = cabinFamily
                    )
                }
            }
        }
    }
}

@Composable
fun RatedPublicationCard(publication: PublicationByOrderResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(publication.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = publication.nameActivity,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBrown,
                    fontFamily = cabinFamily
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = publication.description,
                    fontSize = 16.sp,
                    color = TextBrown.copy(alpha = 0.8f),
                    fontFamily = cabinFamily
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .background(LightBrown.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = PrimaryBrown,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Rating promedio: ${publication.averageRating}",
                        fontSize = 14.sp,
                        color = TextBrown,
                        fontFamily = cabinFamily
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownMenuItem(onClick: () -> Unit, content: @Composable () -> Unit) {
    androidx.compose.material3.DropdownMenuItem(
        onClick = onClick,
        text = content,
        colors = MenuDefaults.itemColors(
            textColor = TextBrown,
            leadingIconColor = PrimaryBrown
        )
    )
}