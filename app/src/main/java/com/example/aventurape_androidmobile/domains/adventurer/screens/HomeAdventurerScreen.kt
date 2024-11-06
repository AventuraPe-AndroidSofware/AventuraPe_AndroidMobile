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
import com.example.aventurape_androidmobile.domains.adventurer.models.ProfileEntrepreneur

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
            Spacer(modifier = Modifier.height(25.dp))
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

            Spacer(modifier = Modifier.height(25.dp))

            // Título "Perfiles de Emprendedores"
            Text(
                text = "Perfiles de Emprendedores",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
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

            Spacer(modifier = Modifier.weight(1f))
            Text(text = "¿Estás listo para dejarte llevar?")
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

@Composable
fun EntrepreneurCard(entrepreneur: ProfileEntrepreneur) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = entrepreneur.imageUrl),
                contentDescription = "Entrepreneur Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = entrepreneur.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = entrepreneur.email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}
