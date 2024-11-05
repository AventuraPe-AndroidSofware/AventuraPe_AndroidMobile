package com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.viewModels.PublicationViewModel
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse

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
            Image(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "Refresh Icon",
                modifier = Modifier
                    .clickable { viewModel.getPublications(entrepreneurId) }
                    .padding(170.dp, 0.dp, 0.dp, 0.dp)
            )
        }

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
            modifier = Modifier.padding(20.dp, 5.dp, 0.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                tint = Color(0xFF6D4C41),
            )
            Text(
                text = "¡Publica tus actividades y comparte con los aventureros!",
                fontSize = 15.sp,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(85.dp, 0.dp, 0.dp, 20.dp)
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

        CardPublications(viewModel, entrepreneurId, navController)

        if (showForm.value) {
            FormActivity(viewModel, navController, entrepreneurId)
        }
    }
}

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

                        viewModel.sendPublication(publicationRequest, entrepreneurId) {
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
                    color = Color(0xFF6D4C41),
                    textAlign = TextAlign.Center
                )
            },
            modifier = Modifier
                .width(400.dp)
                .height(600.dp),
            text = {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.width(300.dp)
                ) {
                    item {
                        Text(text = "Nombre de la actividad",
                            fontFamily = cabinFamily,
                            fontSize = 16.sp,
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp))
                        OutlinedTextField(value = nombreActividad,
                            onValueChange = { nombreActividad = it },
                            placeholder = { Text(text="Ejem: Taller de pintura",
                                fontFamily = cabinFamily) },
                            modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 5.dp),)
                    }
                    item {
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
                    }
                    item {
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
                    }
                    item {
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
                    }
                    item {
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
                    }
                    item {
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
            }
        )
    }
}

@Composable
fun CardPublications(viewModel: PublicationViewModel, entrepreneurId: Long, navController: NavController) {
    val publications by viewModel.publications
    val snackbarHostState = remember { SnackbarHostState() }
    val showEditForm = remember { mutableStateOf(false) }
    var selectedPublication by remember { mutableStateOf<PublicationResponse?>(null) }

    SnackbarHost(hostState = snackbarHostState)

    LaunchedEffect(Unit) {
        viewModel.getPublications(entrepreneurId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(publications) { publication ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                border = BorderStroke(1.dp, Color(0xFFA6A2A2)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .padding(16.dp, 16.dp, 16.dp, 0.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
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
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nombre: ",
                            fontFamily = cabinFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 0.dp)
                        )
                        Text(
                            text = publication.nameActivity,
                            fontFamily = cabinFamily,
                            fontSize = 16.sp,
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(0.dp, 5.dp, 2.dp, 4.dp)
                        )
                        Text(
                            text = "Descripción: ",
                            fontFamily = cabinFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 0.dp)
                        )
                        Text(
                            text = publication.description,
                            fontFamily = cabinFamily,
                            fontSize = 16.sp,
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(0.dp, 5.dp, 2.dp, 10.dp)
                        )

                        Row {
                            Text(
                                text = "Cant.Personas: ",
                                fontFamily = cabinFamily,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF000000),
                                modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp)
                            )
                            Text(
                                text = publication.cantPeople.toString(),
                                fontFamily = cabinFamily,
                                fontSize = 16.sp,
                                color = Color(0xFF000000),
                                modifier = Modifier.padding(2.dp, 5.dp, 2.dp, 5.dp)
                            )
                        }

                        // Row for Edit and Delete Buttons
                        Row(
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            // Edit Button
                            TextButton(
                                onClick = {
                                    selectedPublication = publication
                                    showEditForm.value = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    containerColor = Color(0xFFE8A63D)
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(text = "Editar",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = cabinFamily,
                                    fontSize = 16.sp)
                            }

                            // Delete Button
                            TextButton(
                                onClick = {
                                    viewModel.deletePublication(publication.Id, entrepreneurId) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            snackbarHostState.showSnackbar("Publicación eliminada correctamente")
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    containerColor = Color(0xFFA61B1B)
                                )
                            ) {
                                Text(text = "Eliminar",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = cabinFamily,
                                    fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
    if (showEditForm.value && selectedPublication != null) {
        FormEditActivity(viewModel, navController, entrepreneurId, selectedPublication!!) {
            showEditForm.value = false
            viewModel.getPublications(entrepreneurId)
        }
    }
}

@Composable
fun FormEditActivity(
    viewModel: PublicationViewModel,
    navController: NavController,
    entrepreneurId: Long,
    publication: PublicationResponse,
    onDismiss: () -> Unit
) {
    var showDialogDatos by remember { mutableStateOf(true) }
    var nombreActividad by remember { mutableStateOf(publication.nameActivity) }
    var descripcion by remember { mutableStateOf(publication.description) }
    var duracion by remember { mutableStateOf(publication.timeDuration) }
    var foto by remember { mutableStateOf(publication.image) }
    var cantidadPersonas by remember { mutableStateOf(publication.cantPeople.toString()) }
    var costo by remember { mutableStateOf(publication.cost.toString()) }
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarHost(hostState = snackbarHostState)

    if (showDialogDatos) {
        AlertDialog(
            onDismissRequest = {
                showDialogDatos = false
                onDismiss()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val publicationRequest = PublicationRequest(
                            Id = publication.Id,
                            entrepreneurId = entrepreneurId,
                            nameActivity = nombreActividad,
                            description = descripcion,
                            timeDuration = duracion,
                            image = foto,
                            cantPeople = cantidadPersonas.toInt(),
                            cost = costo.toInt()
                        )

                        viewModel.updatePublication(publicationRequest) { success ->
                            if (success) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    snackbarHostState.showSnackbar("Actividad actualizada correctamente")
                                    showDialogDatos = false
                                    onDismiss()
                                }
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
                    showDialogDatos = false
                    onDismiss()
                },
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
                    color = Color(0xFF6D4C41),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.width(300.dp)
                ) {
                    item {
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
                    }
                    item {
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
                    }
                    item {
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
                    }
                    item {
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
                    }
                    item {
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
                    }
                    item {
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
            },
            modifier = Modifier
                .width(400.dp)
                .height(600.dp)
        )
    }
}