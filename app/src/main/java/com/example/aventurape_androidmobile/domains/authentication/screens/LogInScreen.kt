// LogInScreen.kt
package com.example.aventurape_androidmobile.domains.authentication.screens

import PreferenceManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.authentication.screens.viewModels.LoginViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.navigation.Roles
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

// Define custom colors
private val PrimaryBrown = Color(0xFF765532)
private val LightBrown = Color(0xFF9B7B5B)
private val DarkBrown = Color(0xFF523A23)
private val BackgroundColor = Color(0xFFF5EDE4)
private val TextPrimaryColor = Color(0xFF2D1810)
private val TextSecondaryColor = Color(0xFF5C4332)

@Composable
fun LogInScreen(viewModel: LoginViewModel, navController: NavHostController) {
    val context = LocalContext.current.applicationContext
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val (currentFocusRequester, nextFocusRequester) = remember { FocusRequester.createRefs() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BackgroundColor, Color.White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_aventurape),
                contentDescription = "Logo image",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Bienvenido!",
                        fontFamily = cabinFamily,
                        fontSize = 16.sp,
                        color = TextSecondaryColor
                    )
                    Text(
                        text = "Iniciar Sesión",
                        fontFamily = cabinFamily,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryColor,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { viewModel.inputCredentials(it, state.password) },
                        label = { Text("Usuario") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBrown,
                            focusedLabelColor = PrimaryBrown,
                            cursorColor = PrimaryBrown
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(currentFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { viewModel.inputCredentials(state.username, it) },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBrown,
                            focusedLabelColor = PrimaryBrown,
                            cursorColor = PrimaryBrown
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(nextFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        })
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.viewModelScope.launch {
                                viewModel.signInUser(context, state.username, state.password)
                                if (state.loginSuccess) {
                                    val userRole = PreferenceManager.getUserRoles(context)
                                    Log.d("Roles", "User roles: $userRole")
                                    Log.d("SharedPreferences", PreferenceManager.getAllPreferences(context).toString())

                                    when {
                                        userRole != null && userRole.contains(Roles.ROLE_ADVENTUROUS.name) -> {
                                            navController.navigate(NavScreenAdventurer.home_adventurer_screen.name)
                                        }
                                        userRole != null && userRole.contains(Roles.ROLE_ENTREPRENEUR.name) -> {
                                            navController.navigate(NavScreenAdventurer.adventure_publication_management.name)
                                        }
                                        else -> {
                                            navController.navigate(NavScreenAdventurer.error_screen.name)
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBrown
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Iniciar Sesión",
                            fontSize = 18.sp,
                            fontFamily = cabinFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ClickableText(
                text = AnnotatedString(
                    text = "¿No tienes una cuenta? Crear cuenta",
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(
                                color = PrimaryBrown,
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Bold
                            ),
                            start = 20,
                            end = 32
                        )
                    )
                ),
                onClick = { offset ->
                    if (offset in 20..32) {
                        navController.navigate(NavScreenAdventurer.select_role_screen.name)
                    }
                }
            )
        }

        if (state.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { viewModel.resetState() },
                confirmButton = {
                    TextButton(
                        onClick = { viewModel.resetState() },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = PrimaryBrown
                        )
                    ) {
                        Text("Entendido")
                    }
                },
                title = {
                    Text(
                        "Error de inicio de sesión",
                        color = TextPrimaryColor,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        state.errorMessage ?: "Ha ocurrido un error desconocido.",
                        color = TextSecondaryColor
                    )
                },
                containerColor = Color.White
            )
        }
    }
}