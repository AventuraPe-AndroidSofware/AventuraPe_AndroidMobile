package com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens.states.PublicationViewModel
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState

@Composable
fun AppPublicationManagement(navController: NavController, entrepreneurId: Long) {
    val showForm = remember { mutableStateOf(false) }
    val viewModel = remember { PublicationViewModel() }
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.aventurapelogo),
            contentDescription = "Logo AventuraPe",
            modifier = Modifier
                .size(width = 150.dp, height = 100.dp)
                .align(Alignment.Start)
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
        )
        Box(
            modifier = Modifier
                .width(350.dp)
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Divider(
                color = Color(0xFF6D4C41),
                thickness = 2.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = "Posts",
            fontSize = 28.sp,
            color = Color(0xFF000000),
            fontWeight = FontWeight.Bold,
            fontFamily = cabinFamily,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(20.dp, 20.dp, 0.dp, 0.dp)
                .clickable { showForm.value = !showForm.value }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Icon",
                modifier = Modifier.size(24.dp),
                alignment = Alignment.Center
            )
            Text(
                text = "Agregar actividad",
                fontSize = 20.sp,
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold,
                fontFamily = cabinFamily,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        CardPublications(viewModel, entrepreneurId)

        if (showForm.value) {
            FormActivity(viewModel, navController, entrepreneurId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormActivity(viewModel: PublicationViewModel, navController: NavController, entrepreneurId: Long) {
    var showDialogDatos by remember { mutableStateOf(true) }
    var nombreActividad by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var cantidadPersonas by remember { mutableStateOf("") }
    var costo by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarHost(hostState = snackbarHostState)

    if (showDialogDatos) {
        AlertDialog(
            onDismissRequest = {
                showDialogDatos = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val publicationRequest = PublicationRequest(
                            Id = 0L, // Assuming the ID is auto-generated by the backend
                            entrepreneurId = entrepreneurId,
                            nameActivity = nombreActividad,
                            description = descripcion,
                            timeDuration = duracion,
                            image = foto,
                            cantPeople = cantidadPersonas.toInt(),
                            cost = costo.toInt()
                        )
                        val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJob2xhIiwiaWF0IjoxNzI2ODgxNjMxLCJleHAiOjE3Mjc0ODY0MzF9.yZNIfIOh7sTkO7loVPSOc2Od8uzLC1QzM0q8phUcO5N7CukljJ_yOAAH_wywAaS3" // Replace with a method to get the token dynamically
                        viewModel.sendPublication(publicationRequest, token, entrepreneurId) {
                            CoroutineScope(Dispatchers.Main).launch {
                                snackbarHostState.showSnackbar("Actividad creada correctamente")
                                showDialogDatos = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF6D4C41)
                    )
                ) {
                    Text(text = "Aceptar",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialogDatos = false },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color(0xFFFFFFFF),
                        containerColor = Color(0xFFA78479)
                    )
                ) {
                    Text(text = "Cancelar",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily)
                }
            },
            title = {
                Text(
                    text = "DETALLES DE LA ACTIVIDAD",
                    fontWeight = FontWeight.Bold,
                    fontFamily = cabinFamily,
                    modifier = Modifier
                        .padding(2.dp, 5.dp, 2.dp, 5.dp)
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Nombre de la actividad",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp))
                    OutlinedTextField(value = nombreActividad,
                        onValueChange = { nombreActividad = it },
                        placeholder = { Text(text="Ejem: Taller de pintura",
                            fontFamily = cabinFamily) },
                        modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 5.dp),
                    )

                    Text(text = "Descripción",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp))
                    OutlinedTextField(value = descripcion,
                        onValueChange = { descripcion = it },
                        placeholder = { Text(text="Descripción",
                            fontFamily = cabinFamily) },
                        modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 3.dp),
                    )

                    Text(text = "Tiempo de duración",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp))
                    OutlinedTextField(value = duracion,
                        onValueChange = { duracion = it },
                        placeholder = { Text(text="Hora o minuto",
                            fontFamily = cabinFamily)  },
                        modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 3.dp),
                    )

                    Text(text = "Foto",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp))
                    OutlinedTextField(value = foto,
                        onValueChange = { foto = it },
                        placeholder = { Text(text="Insertar foto",
                            fontFamily = cabinFamily)  },
                        modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 3.dp),
                    )

                    Text(text = "Cant. Personas",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp))
                    OutlinedTextField(value = cantidadPersonas,
                        onValueChange = { cantidadPersonas = it },
                        placeholder = { Text(text="1,2..",
                            fontFamily = cabinFamily)  },
                        modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 3.dp),
                    )

                    Text(text = "Costo",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp))
                    OutlinedTextField(value = costo,
                        onValueChange = { costo = it },
                        placeholder = { Text(text="S/.",
                            fontFamily = cabinFamily)  },
                        modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 3.dp),
                    )
                }
            }
        )
    }
}

@Composable
fun CardPublications(viewModel: PublicationViewModel, entrepreneurId: Long) {
    val publications by viewModel.publications

    LaunchedEffect(Unit) {
        val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJob2xhIiwiaWF0IjoxNzI2ODgxNjMxLCJleHAiOjE3Mjc0ODY0MzF9.yZNIfIOh7sTkO7loVPSOc2Od8uzLC1QzM0q8phUcO5N7CukljJ_yOAAH_wywAaS3" // Replace with a method to get the token dynamically
        viewModel.getPublications(token, entrepreneurId)
    }

    publications.forEach { publication ->
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6E6))
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.aventurapelogo), //aca modificar
                    contentDescription = "Logo AventuraPe",
                    modifier = Modifier
                        .size(width = 150.dp, height = 100.dp)
                        .align(Alignment.Start)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = publication.nameActivity,
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp)
                    )
                    Text(
                        text = publication.description,
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp)
                    )
                }
            }
        }
    }
}