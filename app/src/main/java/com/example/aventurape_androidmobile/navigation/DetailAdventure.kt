package com.example.aventurape_androidmobile.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.ui.theme.cabinFamily

@Composable
fun DetailView(navController: NavController, adventure: Adventure) {
    var comment by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Icono de regreso
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Regresar",
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(8.dp)
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

        // Nombre de la actividad
        Text(
            text = adventure.nameActivity,
            fontSize = 24.sp,
            fontFamily = cabinFamily,// Cambia esto si tienes una fuente personalizada
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Descripción y detalles adicionales
        Text(
            text = "Descripción: ${adventure.description}",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Tiempo de duración: ${adventure.timeDuration}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "Cantidad de personas: ${adventure.cantPeople}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "Costo: ${adventure.cost}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Sección de calificación con estrellas de color
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFE8A63D),
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "Califica esta actividad",
                fontSize = 16.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            repeat(5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
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
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Enviar comentario",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { /* Acción para enviar comentario */ }
            )
        }

        // Lista de comentarios con íconos de usuario
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Usuario: Muy buena aventura!", fontSize = 14.sp, modifier = Modifier.padding(vertical = 4.dp))
            Text(text = "Usuario: Me encantó!", fontSize = 14.sp, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}