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
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import com.google.ai.client.generativeai.Chat

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
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.aventurapelogo),
                contentDescription = "Logo AventuraPe",
                modifier = Modifier
                    .size(width = 150.dp, height = 100.dp)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "Refresh Icon",
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)
                    .padding(5.dp, 0.dp, 0.dp, 0.dp)
                    .clickable {
                        when (selectedTab) {
                            "Mis publicaciones" -> statisticsViewModel.getUserAdventures(entrepreneurId)
                            "Más comentados" -> statisticsViewModel.getTop5AdventuresByComments()
                            "Mejores puntuaciones" -> statisticsViewModel.getFavoritePublicationsByProfileIdOrderedByRating(entrepreneurId)
                        }
                    }
            )
        }
        Divider(
            color = Color(0xFF6D4C41),
            thickness = 2.dp,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = selectedTab)
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                tabs.forEach { tab ->
                    DropdownMenuItem(onClick = {
                        selectedTab = tab
                        expanded = false
                    }) {
                        Text(text = tab)
                    }
                }
            }
        }

        if (isLoading || statisticsViewModel.isLoadingTopAdventures) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            when (selectedTab) {
                "Mis publicaciones" -> {
                    Text(
                        text = "Estadisticas",
                        fontSize = 28.sp,
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = "¡Tus mejores publicaciones las encuentras aquí!",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 30.dp, top = 15.dp, end = 5.dp, bottom = 10.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(userAdventures.value) { adventure ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp),
                                border = BorderStroke(1.dp, Color(0xFFA6A2A2)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(adventure.image)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(350.dp, 200.dp)
                                            .fillMaxWidth(),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = adventure.nameActivity,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = adventure.description,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Comentarios: ${statisticsViewModel.countCommentsForAdventure(adventure.Id)}",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
                "Más comentados" -> {
                    Text(
                        text = "Top 5 de aventuras con más comentarios",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 10.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(topAdventures.value) { adventure ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp),
                                border = BorderStroke(1.dp, Color(0xFFA6A2A2)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(adventure.image)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(350.dp, 200.dp)
                                            .fillMaxWidth(),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = adventure.nameActivity,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = adventure.description,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Comentarios: ${statisticsViewModel.countCommentsForAdventure(adventure.Id)}",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
                "Mejores puntuaciones" -> {
                    Text(
                        text = "Top rating de las mejores aventuras",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 10.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(topRatedPublications.value) { publication ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp),
                                border = BorderStroke(1.dp, Color(0xFFA6A2A2)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(publication.image)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(350.dp, 200.dp)
                                            .fillMaxWidth(),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = publication.nameActivity,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = publication.description,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Promedio de rating: ${publication.averageRating}",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuItem(onClick: () -> Unit, content: @Composable () -> Unit) {
    androidx.compose.material3.DropdownMenuItem(
        onClick = onClick,
        text = content
    )
}