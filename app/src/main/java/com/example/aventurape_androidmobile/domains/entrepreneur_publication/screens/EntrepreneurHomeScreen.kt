package com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.viewModels.PublicationViewModel
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Define color scheme
private val PrimaryColor = Color(0xFF765532)
private val LightBrown = Color(0xFF9B7B5B)
private val DarkBrown = Color(0xFF5A3E21)
private val AccentBrown = Color(0xFFB39B85)
private val BackgroundColor = Color(0xFFF5F0EB)

@Composable
fun AppPublicationManagement(navController: NavController, entrepreneurId: Long) {
    val showForm = remember { mutableStateOf(false) }
    val viewModel = remember { PublicationViewModel() }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getPublications(entrepreneurId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Header(onRefresh = { viewModel.getPublications(entrepreneurId) })

            // Title Section
            TitleSection(
                onAddClick = { showForm.value = !showForm.value }
            )

            // Publications List
            CardPublications(viewModel, entrepreneurId, navController)

            if (showForm.value) {
                FormActivity(viewModel, navController, entrepreneurId)
            }
        }
    }
}

@Composable
private fun Header(onRefresh: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.aventurapelogo),
            contentDescription = "Logo AventuraPe",
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )
    }
}

@Composable
private fun TitleSection(onAddClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Posts",
            fontSize = 32.sp,
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            fontFamily = cabinFamily,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "¡Publica tus actividades y comparte con los aventureros!",
                fontSize = 16.sp,
                color = DarkBrown,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Agregar actividad",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CardPublications(viewModel: PublicationViewModel, entrepreneurId: Long, navController: NavController) {
    val publications by viewModel.publications

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(publications) { publication ->
            PublicationCard(
                publication = publication,
                onEdit = {
                    // Handle edit
                },
                onDelete = {
                    viewModel.deletePublication(publication.Id, entrepreneurId) {}
                }
            )
        }
    }
}

@Composable
private fun PublicationCard(
    publication: PublicationResponse,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, LightBrown, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(publication.image)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = publication.nameActivity,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = publication.description,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DetailItem(
                        icon = Icons.Default.Person,
                        text = "${publication.cantPeople} personas"
                    )
                    DetailItem(
                        icon = Icons.Filled.Check,
                        text = "S/. ${publication.cost}"
                    )
                    DetailItem(
                        icon = Icons.Default.DateRange,
                        text = publication.timeDuration
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onEdit,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightBrown
                        )
                    ) {
                        Text("Editar")
                    }

                    Button(
                        onClick = onDelete,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = DarkBrown
        )
    }
}

// ... (FormActivity and FormEditActivity remain similar but with updated colors and styling)
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

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            foto = it.toString()
        }
    }

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
                            trailingIcon = {
                                IconButton(onClick = { launcher.launch("image/*") }) {
                                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "Select Image")
                                }
                            }
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