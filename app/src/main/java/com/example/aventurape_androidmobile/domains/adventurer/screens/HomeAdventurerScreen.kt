import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.HomeAdventurerViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import androidx.compose.ui.res.stringResource

@Composable
fun HomeAdventurerScreen(viewModel: HomeAdventurerViewModel, navController: NavController) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
            Spacer(modifier = Modifier.height(20.dp))
            // Título "Posts"
            Text(
                text = "Posts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
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

            // Espacio entre la lista y el botón
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "¿Estás listo para dejarte llevar?")
            // Botón "SORPRÉNDEME"
            Button(
                onClick = { /* Acción para el botón */ },
                modifier = Modifier
                    .widthIn()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "SORPRÉNDEME", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun AdventureCard(adventure: Adventure) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = rememberImagePainter(data = adventure.image),
                contentDescription = "Adventure Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = adventure.nameActivity, style = MaterialTheme.typography.titleLarge)
                Text(text = adventure.description, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Duration: ${adventure.timeDuration} mins", style = MaterialTheme.typography.labelSmall)
                Text(text = "Cost: ${adventure.cost}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
