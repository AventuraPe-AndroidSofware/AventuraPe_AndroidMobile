import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.HomeAdventurerViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.adventurer.models.ProfileEntrepreneur
import com.example.aventurape_androidmobile.ui.theme.cabinFamily

@Composable
fun HomeAdventurerScreen(viewModel: HomeAdventurerViewModel, navController: NavController) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
        viewModel.loadEntrepreneurs() // Cargar los emprendedores
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                    color = Color(0xFF6D4C41),
                    thickness = 2.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Título "Perfiles de Emprendedores"
            Text(
                text = "Emprendedores",
                fontSize = 28.sp,
                color = Color(0xFF6D4C41),
                fontWeight = FontWeight.Bold,
                fontFamily = cabinFamily,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 15.dp, bottom = 3.dp)
            )
            // Lista horizontal de perfiles de emprendedores
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.entrepreneurs) { entrepreneur ->
                    EntrepreneurCard(entrepreneur = entrepreneur)
                }
            }

            Text(
                text = "Posts",
                fontSize = 28.sp,
                color = Color(0xFF6D4C41),
                fontWeight = FontWeight.Bold,
                fontFamily = cabinFamily,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 3.dp)
            )

            // Lista de publicaciones
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.activities) { adventure ->
                    AdventureCard(adventure = adventure)
                }
            }

            Text(
                text = "¿Estás listo para dejarte llevar?",
                color = Color(0xFF4B342C),
                fontFamily = cabinFamily
            )
            Button(
                onClick = { /* Acción para el botón */ },
                modifier = Modifier
                    .widthIn()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6D4C41)
                )
            ) {
                Text(
                    text = "SORPRÉNDEME",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White, // Text color
                    fontFamily = cabinFamily
                )
            }
        }
    }
}

@Composable
fun AdventureCard(adventure: Adventure) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(3.dp),
        border = BorderStroke(1.dp, Color(0xFFA6A2A2)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Image(
                painter = rememberImagePainter(adventure.image),
                contentDescription = null,
                modifier = Modifier
                    .padding(13.dp, 13.dp, 13.dp, 0.dp)
                    .size(230.dp, 160.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Actividad: ",
                fontFamily = cabinFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000),
                modifier = Modifier.padding(18.dp, 5.dp, 2.dp, 0.dp)
            )
            Text(
                text = adventure.nameActivity,
                fontFamily = cabinFamily,
                modifier = Modifier.padding(18.dp, 0.dp, 2.dp, 4.dp),
                fontSize = 16.sp,
                color = Color(0xFF000000),
            )
        }
    }
}

@Composable
fun EntrepreneurCard(entrepreneur: ProfileEntrepreneur) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        border = BorderStroke(1.dp, Color(0xFFA6A2A2)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(entrepreneur.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp).padding(5.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = entrepreneur.name,
                fontFamily = cabinFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = entrepreneur.email,
                fontFamily = cabinFamily,
                fontSize = 14.sp,
                color = Color(0xFF000000),
                modifier = Modifier.padding(start = 15.dp)
            )
        }
    }
}
