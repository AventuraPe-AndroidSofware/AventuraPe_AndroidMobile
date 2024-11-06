package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.authentication.screens.viewModels.SignUpViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navController: NavHostController) {
    val state = viewModel.state

    // Mejora de la interfaz de usuario (Salto de campos) Experimental
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val (currentFocusRequester, nextFocusRequester) = remember { FocusRequester.createRefs() }
    val context = LocalContext.current // Move LocalContext.current here

    // Efecto lanzado cuando cambia el estado de signupSuccess
    LaunchedEffect(state.signupSuccess) {
        if (state.signupSuccess) {
            viewModel.resetRole()
            navController.navigate(NavScreenAdventurer.login_screen.name)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_aventurape),
            contentDescription = "Logo image",
            modifier = Modifier.size(200.dp)
        )
        Text(text = "Bienvenido !!", fontFamily = cabinFamily, fontSize = 15.sp, fontWeight = FontWeight.Normal)
        Text(text = "Sign Up", fontFamily = cabinFamily, fontSize = 42.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.size(10.dp))

        Text(text = "User", fontFamily = cabinFamily, fontWeight = FontWeight.Normal)
        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.inputData(it, state.password, state.confirmPassword) },
            label = {
                Text(text = "User", fontFamily = cabinFamily, fontWeight = FontWeight.Normal)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.focusRequester(currentFocusRequester)
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(text = "Password", fontFamily = cabinFamily, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.size(5.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.inputData(state.username, it, state.confirmPassword) },
            label = {
                Text(text = "Password", fontFamily = cabinFamily, fontWeight = FontWeight.Bold)
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            modifier = Modifier.focusRequester(nextFocusRequester)
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(text = "Confirm password", fontFamily = cabinFamily, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.size(5.dp))
        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = { viewModel.inputData(state.username, state.password, it) },
            label = {
                Text(text = "Confirm password", fontFamily = cabinFamily, fontWeight = FontWeight.Bold)
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            modifier = Modifier.focusRequester(nextFocusRequester)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                if (state.password == state.confirmPassword) {
                    viewModel.viewModelScope.launch {
                        viewModel.signUpUser(state.username, state.password)
                        // Obtener el rol del usuario después del registro
                        val userRole = PreferenceManager.getUserRoles(context)
                        when {
                            userRole != null && userRole.contains("ROLE_ADVENTUROUS") -> {
                                navController.navigate(NavScreenAdventurer.home_adventurer_screen.name)
                            }
                            userRole != null && userRole.contains("ROLE_ENTREPRENEUR") -> {
                                navController.navigate(NavScreenAdventurer.adventure_publication_management.name)
                            }
                            else -> {
                                navController.navigate(NavScreenAdventurer.error_screen.name)
                            }
                        }
                    }
                } else {
                    viewModel.validatePassword(state.password, state.confirmPassword)
                }
            }
        ) {
            Text("Sign Up", fontSize = 22.sp, fontFamily = cabinFamily, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.size(10.dp))

        // Texto para iniciar sesión
        ClickableText(
            text = AnnotatedString(
                text = "¿Ya tienes una cuenta? Inicia sesión",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        ),
                        start = 23,
                        end = 36
                    )
                )
            ),
            onClick = { offset ->
                if (offset in 23..36) {
                    viewModel.resetRole()
                    navController.navigate(NavScreenAdventurer.login_screen.name)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Mostrar el popup si hay un error
        if (state.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { /* Ignorar o hacer alguna acción si se quiere cerrar el popup */ },
                confirmButton = {
                    Button(onClick = { viewModel.resetStateExceptRole() }) {
                        Text("OK")
                    }
                },
                title = { Text("Error de registro de usuario") },
                text = { Text(state.errorMessage ?: "Ha ocurrido un error desconocido.") }
            )
        }
    }
}