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
    val entrepreneurId = PreferenceManager.getUserId(context)
    var selectedTab by remember { mutableStateOf("Mis publicaciones") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(selectedTab) {
        isLoading = true
        if (selectedTab == "Mis publicaciones") {
            statisticsViewModel.getUserAdventures(entrepreneurId)
        } else {
            statisticsViewModel.getTop5AdventuresByComments()
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
                        if (selectedTab == "Más comentados") {
                            statisticsViewModel.getTop5AdventuresByComments()
                        } }
            )
        }
        Divider(
            color = Color(0xFF6D4C41),
            thickness = 2.dp,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
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
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { selectedTab = "Mis publicaciones" },
                        modifier = Modifier.padding(15.dp, 5.dp, 4.dp, 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF6D4C41)
                        )
                    ) {
                        Text(text = "Mis publicaciones", fontSize = 14.sp)
                    }
                    Button(
                        onClick = { selectedTab = "Más comentados" },
                        modifier = Modifier.padding(4.dp, 5.dp, 0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF6D4C41)
                        )
                    ) {
                        Text(text = "Más comentados", fontSize = 14.sp)
                    }
                }
            }
            if (isLoading || statisticsViewModel.isLoadingTopAdventures) {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            } else {
                if (selectedTab == "Mis publicaciones") {
                    item {
                        Row(
                            modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                contentDescription = null,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                tint = Color(0xFF6D4C41),
                            )
                            Text(
                                text = "¡Puedes ver tus publicaciones con mayor interacción!",
                                fontSize = 15.sp,
                            )
                        }
                    }
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
                } else if (selectedTab == "Más comentados") {
                    item {
                        Text(
                            text = "Top 5 de aventuras con más comentarios",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 10.dp)
                        )
                    }
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
        }
    }
}