import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import com.example.aventurape_androidmobile.userProfileManagement.screens.AccountAdventurerDestination
import com.example.aventurape_androidmobile.userProfileManagement.screens.states.ProfileViewModelA
import kotlinx.coroutines.launch

@Composable
fun AccountInformationA(navController: NavController, viewModelA: ProfileViewModelA) {
    val state = viewModelA.state
    val scrollState = rememberScrollState()
    val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJob2xhIiwiaWF0IjoxNzI2OTU5NzAwLCJleHAiOjE3Mjc1NjQ1MDB9.Cow3sXgCJVcJ7Bho27rFPU6_XQkKMqeyZ3hTiIPO9eb_5g4qShnZKNeWakdBOLM6"

    LaunchedEffect(viewModelA.state.profileCompletedSucces) {
        if (viewModelA.state.profileCompletedSucces) {
            viewModelA.resetState()
            navController.navigate(NavScreenAdventurer.AccountA.name)
        }
    }

    Box(modifier = Modifier.verticalScroll(scrollState)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Absolute.Right,
            ) {
                Button(
                    onClick = {
                        Log.d("AccountInformationA", "Save button clicked")
                        viewModelA.viewModelScope.launch {
                            try {
                                viewModelA.completeProfile(
                                    state.firstName, state.lastName, state.email, state.street,
                                    state.number, state.city, state.postalCode, state.country, state.gender,
                                    token
                                )
                                Log.d("AccountInformationA", "Profile completed successfully")
                            } catch (e: Exception) {
                                Log.e("AccountInformationA", "Error completing profile", e)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xBC765532),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Save",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Nombre", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.firstName,
                    onValueChange = { viewModelA.inputProfileData(it, lastName = state.lastName, email = state.email, street = state.street, number = state.number, city = state.city, postalCode = state.postalCode, country= state.country, gender=state.gender) } ,
                    label={Text("Nombre",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Apellido", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.lastName,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, it, email = state.email, street = state.street, number = state.number, city = state.city, postalCode = state.postalCode, country= state.country, gender=state.gender) } ,
                    label={Text("Apellido",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Email", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.email,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, lastName = state.lastName, it, street = state.street, number = state.number, city = state.city, postalCode = state.postalCode, country= state.country, gender=state.gender) } ,
                    label={Text("ejemplo@ejemplo.com",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Dirección", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.street,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, lastName = state.lastName, email = state.email, it, number = state.number, city = state.city, postalCode = state.postalCode, country= state.country, gender=state.gender) } ,
                    label={Text("calle",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Numero", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.number,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, lastName = state.lastName, email = state.email, street = state.street, it, city = state.city, postalCode = state.postalCode, country= state.country, gender=state.gender) } ,
                    label={Text("+51 999 999 999",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Ciudad", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.city ,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, lastName = state.lastName, email = state.email, street = state.street, number = state.number, it, postalCode = state.postalCode, country= state.country, gender=state.gender) } ,
                    label={Text("Ciudad",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Codigo postal", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.postalCode ,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, lastName = state.lastName, email = state.email, street = state.street, number = state.number, city = state.city, it, country= state.country, gender=state.gender) } ,
                    label={Text("00706",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Pais", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.country ,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, lastName = state.lastName, email = state.email, street = state.street, number = state.number, city = state.city, postalCode = state.postalCode, it, gender=state.gender) } ,
                    label={Text("Pais",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Column(modifier= Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start){
                Text(text = "Genero", modifier=Modifier.width(100.dp),
                    color=Color(0xBC765532), fontSize = 17.sp)
                OutlinedTextField(modifier=Modifier.fillMaxWidth(),
                    value = state.gender ,
                    onValueChange = { viewModelA.inputProfileData(firstName = state.firstName, lastName = state.lastName, email = state.email, street = state.street, number = state.number, city = state.city, postalCode = state.postalCode, country= state.country, it) } ,
                    label={Text("Genero",fontFamily = cabinFamily, fontWeight = FontWeight.Normal)}
                )
            }
        }
    }
}
