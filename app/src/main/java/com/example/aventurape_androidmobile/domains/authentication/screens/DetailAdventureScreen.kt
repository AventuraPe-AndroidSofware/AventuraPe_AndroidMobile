package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.Beans.Review
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Importa SnackbarHostState y SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import com.example.aventurape_androidmobile.Beans.Comment
import com.example.aventurape_androidmobile.domains.authentication.screens.states.getComments
import com.example.aventurape_androidmobile.domains.authentication.screens.states.sendReview

@Composable
fun DetailView(navController: NavController, adventure: Adventure) {
    var comment by remember { mutableStateOf(TextFieldValue("")) }
    var rating by remember { mutableStateOf(0) }
    var hoverRating by remember { mutableStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val comments = remember { mutableStateOf<List<Comment>>(emptyList()) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJtaW5pIiwiaWF0IjoxNzI2ODc5NTk4LCJleHAiOjE3Mjc0ODQzOTh9.4wVqxFWPsjoHyPpiktdzz7BXnEsZZrbGmtFyfHCPRI3fzJh6FUHrRnm1T8gCSuVQ"
        comments.value = getComments(token, adventure.Id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState) // Añade el modificador de scroll
    ) {
        // Icono de regreso
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Regresar",
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(8.dp)
        )
        // Nombre de la actividad
        Text(
            text = adventure.nameActivity,
            fontSize = 24.sp,
            fontFamily = cabinFamily,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        // Imagen principal con fondo color claro y bordes redondeados
        Box(
            modifier = Modifier
                .background(Color(0xFFF0CCAA), shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(adventure.image)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        // Descripción y detalles adicionales
        Text(
            text = "Descripción: ${adventure.description}",
            fontSize = 19.sp,
            fontFamily = cabinFamily,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Tiempo de duración: ${adventure.timeDuration}",
            fontSize = 19.sp,
            fontFamily = cabinFamily,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "Cantidad de personas: ${adventure.cantPeople}",
            fontSize = 19.sp,
            fontFamily = cabinFamily,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "Costo: ${adventure.cost}",
            fontSize = 19.sp,
            fontFamily = cabinFamily,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Sección de calificación con estrellas de color
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFE8A63D),
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "Calificar esta actividad",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            repeat(5) { index ->
                val starIndex = index + 1
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (starIndex <= (hoverRating.takeIf { it > 0 } ?: rating)) Color(0xFFE8A63D) else Color.Gray,
                    modifier = Modifier
                        .size(32.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    hoverRating = starIndex
                                },
                                onTap = {
                                    rating = starIndex
                                    hoverRating = 0
                                }
                            )
                        }
                )
            }
        }

        // Campo de texto para comentarios
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Comentarios",
                fontSize = 16.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF0CCAA), shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = comment,
                onValueChange = { comment = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (comment.text.isEmpty()) {
                        Text(
                            text = "Agrega un comentario",
                            color = Color.Gray,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    innerTextField()
                }
            )
        }
       Spacer(modifier = Modifier.height(18.dp))
        // Botón de enviar comentario
        Button(
            onClick = {
                val review = Review(
                    id = adventure.Id,
                    publicationId = adventure.Id,
                    content = comment.text,
                    rating = rating * 2
                )
                val token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzdHJpbmciLCJpYXQiOjE3MjY4NjQ2MTYsImV4cCI6MTcyNzQ2OTQxNn0._Ek2Q2Od63eiHGM8x76sWUT_n6bTD58ibi5x3XyOWWeb7AVLhZ-Mq5LraMp58afj"
                sendReview(review, token, adventure.Id) {
                    comments.value = getComments(token, adventure.Id)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    snackbarHostState.showSnackbar("Review sent successfully")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8A63D)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Enviar comentario",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Review",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 18.dp)
            )
        }

        // SnackbarHost para mostrar mensajes
        SnackbarHost(hostState = snackbarHostState)

        // Mostrar los comentarios
        Text(
            text = "Comentarios",
            fontSize = 19.sp,
            fontFamily = cabinFamily,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        comments.value.forEach { comment ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Column(
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text(
                        text = "User: ${comment.id}", // Mostrar el username
                        fontSize = 16.sp,
                        fontFamily = cabinFamily,
                        color = Color.Black
                    )
                    Text(
                        text = "Comentario: ${comment.content}",
                        fontSize = 16.sp,
                        fontFamily = cabinFamily,
                        color = Color.Black
                    )
                    Text(
                        text = "Rating: ${comment.rating}",
                        fontSize = 16.sp,
                        fontFamily = cabinFamily,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }

    }
}

