package com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
        Text("AventuraPe", fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = cabinFamily, color = PrimaryColor)
    }
}

@Composable
private fun TitleSection(onAddClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Posts",
            fontSize = 28.sp,
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
    val snackbarHostState = remember { SnackbarHostState() }
    val showEditForm = remember { mutableStateOf(false) }
    var selectedPublication by remember { mutableStateOf<PublicationResponse?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0EA))
    ) {
        SnackbarHost(hostState = snackbarHostState)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(publications) { publication ->
                PublicationCard(
                    publication = publication,
                    onEdit = {
                        selectedPublication = publication
                        showEditForm.value = true
                    },
                    onDelete = {
                        viewModel.deletePublication(publication.Id, entrepreneurId) {
                            CoroutineScope(Dispatchers.Main).launch {
                                snackbarHostState.showSnackbar("Publicación eliminada correctamente")
                            }
                        }
                    }
                )
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
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Editar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = onDelete,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Eliminar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
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
        horizontalArrangement = Arrangement.Center
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
            color = Color.DarkGray
        )
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
    var useGallery by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }

    val primaryColor = Color(0xFF765532)
    val secondaryColor = Color(0xFF8B6B47)
    val lightColor = Color(0xFFA69081)
    val backgroundColor = Color(0xFFF5F0EB)

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            foto = it.toString()
        }
    }

    SnackbarHost(hostState = snackbarHostState)

    if (showDialogDatos) {
        AlertDialog(
            onDismissRequest = { showDialogDatos = false },
            confirmButton = {
                Button(
                    onClick = {
                        val publicationRequest = PublicationRequest(
                            Id = 0L,
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
                        containerColor = primaryColor
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Crear Actividad",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily,
                        fontSize = 16.sp
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialogDatos = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = primaryColor
                    ),
                    border = BorderStroke(1.dp, primaryColor),
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        fontWeight = FontWeight.Medium,
                        fontFamily = cabinFamily,
                        fontSize = 16.sp
                    )
                }
            },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nueva Actividad",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily,
                        fontSize = 24.sp,
                        color = primaryColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = lightColor, thickness = 1.dp)
                }
            },
            modifier = Modifier
                .width(400.dp)
                .height(680.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = backgroundColor,
            text = {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        FormField(
                            label = "Nombre de la actividad",
                            value = nombreActividad,
                            onValueChange = { nombreActividad = it },
                            placeholder = "Ejem: Taller de pintura",
                            primaryColor = primaryColor
                        )
                    }
                    item {
                        FormField(
                            label = "Descripción",
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            placeholder = "Descripción de la actividad",
                            primaryColor = primaryColor,
                            singleLine = false,
                            minLines = 3
                        )
                    }
                    item {
                        FormField(
                            label = "Duración",
                            value = duracion,
                            onValueChange = { duracion = it },
                            placeholder = "Ej: 2 horas",
                            primaryColor = primaryColor
                        )
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Usar galería",
                                color = primaryColor,
                                fontWeight = FontWeight.Medium
                            )
                            Switch(
                                checked = useGallery,
                                onCheckedChange = { useGallery = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = primaryColor,
                                    uncheckedThumbColor = lightColor
                                )
                            )
                        }
                    }
                    item {
                        if (useGallery) {
                            FormField(
                                label = "Foto",
                                value = foto,
                                onValueChange = { foto = it },
                                placeholder = "Seleccionar imagen",
                                primaryColor = primaryColor,
                                trailingIcon = {
                                    IconButton(
                                        onClick = { launcher.launch("image/*") }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Add,
                                            contentDescription = "Seleccionar imagen",
                                            tint = primaryColor
                                        )
                                    }
                                }
                            )
                        } else {
                            FormField(
                                label = "Foto (URL)",
                                value = foto,
                                onValueChange = { foto = it },
                                placeholder = "URL de la imagen",
                                primaryColor = primaryColor
                            )
                        }
                    }
                    item {
                        if (foto.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(foto)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                FormField(
                                    label = "Personas",
                                    value = cantidadPersonas,
                                    onValueChange = { cantidadPersonas = it },
                                    placeholder = "Ej: 10",
                                    primaryColor = primaryColor,
                                    keyboardType = KeyboardType.Number
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                FormField(
                                    label = "Costo (S/.)",
                                    value = costo,
                                    onValueChange = { costo = it },
                                    placeholder = "Ej: 50",
                                    primaryColor = primaryColor,
                                    keyboardType = KeyboardType.Number
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    primaryColor: Color,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontFamily = cabinFamily,
            fontSize = 14.sp,
            color = primaryColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = cabinFamily,
                    color = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor.copy(alpha = 0.5f),
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = primaryColor.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(8.dp),
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            minLines = minLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
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

    val primaryColor = Color(0xFF765532)
    val secondaryColor = Color(0xFF8B6B47)
    val lightColor = Color(0xFFA69081)
    val backgroundColor = Color(0xFFF5F0EB)

    SnackbarHost(hostState = snackbarHostState)

    if (showDialogDatos) {
        AlertDialog(
            onDismissRequest = {
                showDialogDatos = false
                onDismiss()
            },
            confirmButton = {
                Button(
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
                        containerColor = primaryColor
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Actualizar",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily,
                        fontSize = 16.sp
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showDialogDatos = false
                        onDismiss()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = primaryColor
                    ),
                    border = BorderStroke(1.dp, primaryColor),
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        fontWeight = FontWeight.Medium,
                        fontFamily = cabinFamily,
                        fontSize = 16.sp
                    )
                }
            },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Editar Actividad",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinFamily,
                        fontSize = 24.sp,
                        color = primaryColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = lightColor, thickness = 1.dp)
                }
            },
            text = {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        FormField(
                            label = "Nombre de la actividad",
                            value = nombreActividad,
                            onValueChange = { nombreActividad = it },
                            placeholder = "Ejem: Taller de pintura",
                            primaryColor = primaryColor
                        )
                    }
                    item {
                        FormField(
                            label = "Descripción",
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            placeholder = "Descripción de la actividad",
                            primaryColor = primaryColor,
                            singleLine = false,
                            minLines = 3
                        )
                    }
                    item {
                        FormField(
                            label = "Duración",
                            value = duracion,
                            onValueChange = { duracion = it },
                            placeholder = "Ej: 2 horas",
                            primaryColor = primaryColor
                        )
                    }
                    item {
                        FormField(
                            label = "Foto",
                            value = foto,
                            onValueChange = { foto = it },
                            placeholder = "URL de la imagen",
                            primaryColor = primaryColor
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                FormField(
                                    label = "Personas",
                                    value = cantidadPersonas,
                                    onValueChange = { cantidadPersonas = it },
                                    placeholder = "Ej: 10",
                                    primaryColor = primaryColor,
                                    keyboardType = KeyboardType.Number
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                FormField(
                                    label = "Costo (S/.)",
                                    value = costo,
                                    onValueChange = { costo = it },
                                    placeholder = "Ej: 50",
                                    primaryColor = primaryColor,
                                    keyboardType = KeyboardType.Number
                                )
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .width(400.dp)
                .height(680.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = backgroundColor
        )
    }
}