import androidx.compose.animation.AnimatedVisibility
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
import com.example.aventurape_androidmobile.domains.adventurer.states.ProfileStateA
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.ProfileViewModelA
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

@Composable
fun AccountInformationA(
    navController: NavController,
    viewModelA: ProfileViewModelA,
    userId: Long
) {
    val state = viewModelA.state
    val scrollState = rememberScrollState()

    LaunchedEffect(userId) {
        viewModelA.checkProfileExists(userId)
    }

    LaunchedEffect(viewModelA.state.profileCompletedSucces) {
        if (viewModelA.state.profileCompletedSucces) {
            viewModelA.resetState()
            navController.navigate(NavScreenAdventurer.account_adventurer_screen.name)
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
                    text = "Información de la cuenta",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xBC765532),
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (viewModelA.profileExists) {
                DisplayProfile(state)
            } else {
                EditProfile(state, viewModelA)
            }
        }
    }
}

@Composable
private fun DisplayProfile(state: ProfileStateA) {
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
            ProfileItem("Nombre completo", state.firstName + " " + state.lastName)
            ProfileItem("Email", state.email)
            Divider(color = Color(0xFFEEE6E0))
            ProfileItem("Dirección", state.street)
            Divider(color = Color(0xFFEEE6E0))
            ProfileItem(
                "Género",
                if (state.gender == "MALE") "Masculino" else if (state.gender == "FEMALE") "Femenino" else state.gender
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfile(
    state: ProfileStateA,
    viewModelA: ProfileViewModelA
) {
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
                        viewModelA.viewModelScope.launch {
                            viewModelA.completeProfile(
                                state.firstName, state.lastName, state.email,
                                state.street, state.number, state.city,
                                state.postalCode, state.country, state.gender
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

            // Form Fields
            ProfileTextField(
                label = "Nombre",
                value = state.firstName,
                onValueChange = { viewModelA.inputProfileData(it, state.lastName, state.email, state.street, state.number, state.city, state.postalCode, state.country, state.gender) }
            )

            ProfileTextField(
                label = "Apellido",
                value = state.lastName,
                onValueChange = { viewModelA.inputProfileData(state.firstName, it, state.email, state.street, state.number, state.city, state.postalCode, state.country, state.gender) }
            )

            ProfileTextField(
                label = "Email",
                value = state.email,
                placeholder = "ejemplo@ejemplo.com",
                onValueChange = { viewModelA.inputProfileData(state.firstName, state.lastName, it, state.street, state.number, state.city, state.postalCode, state.country, state.gender) }
            )

            ProfileTextField(
                label = "Dirección",
                value = state.street,
                placeholder = "Calle",
                onValueChange = { viewModelA.inputProfileData(state.firstName, state.lastName, state.email, it, state.number, state.city, state.postalCode, state.country, state.gender) }
            )

            ProfileTextField(
                label = "Numero",
                value = state.number,
                placeholder = "+51 999 999 999",
                onValueChange = { viewModelA.inputProfileData(state.firstName, state.lastName, state.email, state.street, it, state.city, state.postalCode, state.country, state.gender) }
            )

            ProfileTextField(
                label = "Ciudad",
                value = state.city,
                onValueChange = { viewModelA.inputProfileData(state.firstName, state.lastName, state.email, state.street, state.number, it, state.postalCode, state.country, state.gender) }
            )

            ProfileTextField(
                label = "Codigo postal",
                value = state.postalCode,
                placeholder = "00706",
                onValueChange = { viewModelA.inputProfileData(state.firstName, state.lastName, state.email, state.street, state.number, state.city, it, state.country, state.gender) }
            )

            ProfileTextField(
                label = "País",
                value = state.country,
                onValueChange = { viewModelA.inputProfileData(state.firstName, state.lastName, state.email, state.street, state.number, state.city, state.postalCode, it, state.gender) }
            )

            // Gender Selection
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Género",
                    color = Color(0xBC765532),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenderOption(
                        selected = state.gender == "MALE",
                        label = "Masculino",
                        onClick = { viewModelA.inputProfileData(state.firstName, state.lastName, state.email, state.street, state.number, state.city, state.postalCode, state.country, "MALE") }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    GenderOption(
                        selected = state.gender == "FEMALE",
                        label = "Femenino",
                        onClick = { viewModelA.inputProfileData(state.firstName, state.lastName, state.email, state.street, state.number, state.city, state.postalCode, state.country, "FEMALE") }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    placeholder: String? = null,
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
            placeholder = placeholder?.let {
                { Text(text = it, fontFamily = cabinFamily) }
            },
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

@Composable
private fun GenderOption(
    selected: Boolean,
    label: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(48.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xBC765532),
                unselectedColor = Color(0xFFDDC8BE)
            )
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp),
            color = Color(0xBC765532)
        )
    }
}