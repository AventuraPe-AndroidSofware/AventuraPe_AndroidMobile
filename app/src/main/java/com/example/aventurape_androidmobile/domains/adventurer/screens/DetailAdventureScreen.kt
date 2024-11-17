package com.example.aventurape_androidmobile.domains.adventurer.screens

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
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.adventurer.models.Review
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import com.example.aventurape_androidmobile.domains.adventurer.models.Comment
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.AdventureViewModel
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.getComments
import com.example.aventurape_androidmobile.domains.adventurer.viewModels.sendReview

@Composable
fun DetailView(navController: NavController, adventure: Adventure, viewModel: AdventureViewModel) {
    var comment by remember { mutableStateOf(TextFieldValue("")) }
    var rating by remember { mutableStateOf(0) }
    var hoverRating by remember { mutableStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val comments = remember { mutableStateOf<List<Comment>>(emptyList()) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getUsers()
        comments.value = getComments(adventure.Id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Regresar",
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(8.dp)
        )
        Text(
            text = adventure.nameActivity,
            fontSize = 24.sp,
            fontFamily = cabinFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000),
            modifier = Modifier.padding(10.dp, 3.dp, 5.dp, 10.dp)
        )
        Box(
            modifier = Modifier
                .background(Color(0xFF6D4C41), shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(adventure.image)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(350.dp, 200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 15.dp, 8.dp, 8.dp),
        ){
            Text(
                text = "Descripción:",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = adventure.description,
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text(
                text = "Tiempo de duración:",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "${adventure.timeDuration}",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text(
                text = "Cantidad de personas:",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "${adventure.cantPeople}",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text(
                text = "Costo:",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "${adventure.cost}",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                color = Color(0xFF000000),
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
                color = Color(0xFF000000),
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = Color(0xFF6D4C41),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Escribe un comentario",
                fontSize = 16.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D4C41),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                    .background(Color(0xFFD2D2D2), shape = RoundedCornerShape(8.dp))
            ) {
                BasicTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    decorationBox = { innerTextField ->
                        if (comment.text.isEmpty()) {
                            Text(
                                text = "Agrega un comentario",
                                color = Color(0xFF756E6D),
                                modifier = Modifier.padding(5.dp),
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {
                val review = Review(
                    id = adventure.Id,
                    publicationId = adventure.Id,
                    content = comment.text,
                    rating = rating * 2
                )
                sendReview(review, adventure.Id) {
                    comments.value = getComments(adventure.Id)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    snackbarHostState.showSnackbar("Review sent successfully")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Enviar comentario",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Review",
                fontSize = 19.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 18.dp)
            )
        }

        SnackbarHost(hostState = snackbarHostState)

        Text(
            text = "Comentarios",
            fontSize = 19.sp,
            fontFamily = cabinFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        comments.value.forEach { comment ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EDEB)),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "User Icon",
                        tint = Color(0xFF7C6760),
                        modifier = Modifier.size(40.dp).padding(8.dp, 8.dp, 0.dp, 0.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        val username = viewModel.getUsernameByAdventureId(comment.adventureId)

                        Row (
                        ) {
                            Text(
                                text = "Usuario:  ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = cabinFamily,
                                color = Color(0xFF4E342E)
                            )
                            Text(
                                text = username ?: "Unknown",
                                fontSize = 16.sp,
                                fontFamily = cabinFamily,
                                color = Color(0xFF4E342E)
                            )
                        }
                        Row() {
                            Text(
                                text = "Comentario:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = cabinFamily,
                                color = Color(0xFF4E342E)
                            )
                            Text(
                                text = comment.content,
                                fontSize = 16.sp,
                                fontFamily = cabinFamily,
                                color = Color(0xFF4E342E),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        //rating
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Rating: ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = cabinFamily,
                                color = Color(0xFF4E342E)
                            )
                            repeat(5) { index ->
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (index < comment.rating / 2) Color(0xFFE8A63D) else Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(7.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}