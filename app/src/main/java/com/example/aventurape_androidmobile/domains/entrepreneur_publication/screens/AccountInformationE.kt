import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.states.ProfileStateE
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.viewModels.ProfileViewModelE
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInformationE(
    navController: NavController,
    viewModelE: ProfileViewModelE,
    userId: Long
) {
    val state = viewModelE.state
    val scrollState = rememberScrollState()

    LaunchedEffect(userId) {
        viewModelE.checkProfileExists(userId)
    }

    LaunchedEffect(viewModelE.state.profileECompletedSucces) {
        if (viewModelE.state.profileECompletedSucces) {
            viewModelE.resetState()
            navController.navigate(NavScreenAdventurer.account_entrepreneur_screen.name)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAF6F3)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Text(
                    text = "Información de la Empresa",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xBC765532),
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (viewModelE.profileExists) {
                DisplayProfile(state)
            } else {
                EditProfile(state, viewModelE)
            }
        }
    }
}

@Composable
private fun DisplayProfile(state: ProfileStateE) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileItem("Nombre de la empresa", state.nameEntrepreneurship)
            Divider(color = Color(0xFFEEE6E0))

            ProfileItem("Ciudad", state.addressCity)
            ProfileItem("País", state.addressCountry)
            ProfileItem("Dirección", state.addressStreet)
            ProfileItem("Código postal", state.addressPostalCode)

            Divider(color = Color(0xFFEEE6E0))

            ProfileItem("Número de contacto", state.addressNumber)
            ProfileItem("Email", state.emailAddress)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfile(state: ProfileStateE, viewModelE: ProfileViewModelE) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Save Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        viewModelE.viewModelScope.launch {
                            viewModelE.completeProfileE(
                                state.nameEntrepreneurship,
                                state.addressCity,
                                state.addressCountry,
                                state.addressNumber,
                                state.addressPostalCode,
                                state.addressStreet,
                                state.emailAddress
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xBC765532)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Guardar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            // Company Information Section
            Text(
                text = "Información General",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xBC765532),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            ProfileTextField(
                label = "Nombre de la empresa",
                value = state.nameEntrepreneurship,
                placeholder = "Ingrese el nombre de su empresa",
                onValueChange = { viewModelE.inputProfileData(it, state.addressCity, state.addressCountry, state.addressNumber, state.addressPostalCode, state.addressStreet, state.emailAddress) }
            )

            // Address Section
            Text(
                text = "Dirección",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xBC765532),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            ProfileTextField(
                label = "Ciudad",
                value = state.addressCity,
                placeholder = "Ingrese su ciudad",
                onValueChange = { viewModelE.inputProfileData(state.nameEntrepreneurship, it, state.addressCountry, state.addressNumber, state.addressPostalCode, state.addressStreet, state.emailAddress) }
            )

            ProfileTextField(
                label = "País",
                value = state.addressCountry,
                placeholder = "Ingrese su país",
                onValueChange = { viewModelE.inputProfileData(state.nameEntrepreneurship, state.addressCity, it, state.addressNumber, state.addressPostalCode, state.addressStreet, state.emailAddress) }
            )

            ProfileTextField(
                label = "Dirección",
                value = state.addressStreet,
                placeholder = "Ingrese su dirección",
                onValueChange = { viewModelE.inputProfileData(state.nameEntrepreneurship, state.addressCity, state.addressCountry, state.addressNumber, state.addressPostalCode, it, state.emailAddress) }
            )

            ProfileTextField(
                label = "Código postal",
                value = state.addressPostalCode,
                placeholder = "Ingrese su código postal",
                onValueChange = { viewModelE.inputProfileData(state.nameEntrepreneurship, state.addressCity, state.addressCountry, state.addressNumber, it, state.addressStreet, state.emailAddress) }
            )

            // Contact Section
            Text(
                text = "Información de Contacto",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xBC765532),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            ProfileTextField(
                label = "Número de contacto",
                value = state.addressNumber,
                placeholder = "+51 999 999 999",
                onValueChange = { viewModelE.inputProfileData(state.nameEntrepreneurship, state.addressCity, state.addressCountry, it, state.addressPostalCode, state.addressStreet, state.emailAddress) }
            )

            ProfileTextField(
                label = "Email",
                value = state.emailAddress,
                placeholder = "empresa@ejemplo.com",
                onValueChange = { viewModelE.inputProfileData(state.nameEntrepreneurship, state.addressCity, state.addressCountry, state.addressNumber, state.addressPostalCode, state.addressStreet, it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            color = Color(0xBC765532),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, fontFamily = cabinFamily) },
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
private fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xBC765532),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color(0xBC765532)
        )
    }
}