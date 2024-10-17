// LogInScreen.kt
package com.example.aventurape_androidmobile.domains.authentication.screens

import PreferenceManager
import android.util.Log
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
import androidx.compose.ui.unit.ExperimentalUnitApi
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

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LogInScreen(viewModel: LoginViewModel, navController: NavHostController){
    val context = LocalContext.current.applicationContext
    val state = viewModel.state

    // Mejora de la interfaz de usuario (Salto de campos) Experimental
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val (currentFocusRequester, nextFocusRequester) = remember { FocusRequester.createRefs() }

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
        Text(text = "Bienvenido !!", fontFamily = cabinFamily,fontSize = 15.sp, fontWeight = FontWeight.Normal)
        Text(text = "Log In", fontFamily = cabinFamily, fontSize = 42.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.size(10.dp))

        Text(text = "User", fontFamily = cabinFamily, fontWeight = FontWeight.Normal)
        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.inputCredentials(it, state.password) },
            label = {
                Text(text = "User", fontFamily = cabinFamily, fontWeight = FontWeight.Normal)
            },
            //Mejora de interfaz de usuario (Salto de campos) Experimental
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
            onValueChange = { viewModel.inputCredentials(state.username, it) },
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
        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.signInUser(context, state.username, state.password)
                    if (state.loginSuccess) {
                        // Obtener el rol del usuario desde el ViewModel o directamente desde PreferenceManager
                        val userRole = PreferenceManager.getUserRoles(context)
                        Log.d("Roles", "User roles: $userRole")
                        Log.d("SharedPreferences", PreferenceManager.getAllPreferences(context).toString())

                        // Navegar según el rol
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
            }
        ) {
            Text("Login", fontSize = 22.sp, fontFamily = cabinFamily, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.size(10.dp))

        // Texto para crear cuenta
        ClickableText(
            text = AnnotatedString(
                text = "¿No tienes una cuenta todavía? Crear cuenta",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        ),
                        start = 31, // Start index of "Crear cuenta"
                        end = 43   // End index of "Crear cuenta"
                    )
                )
            ),
            onClick = { offset ->
                if (offset in 31..43) {
                    navController.navigate(NavScreenAdventurer.select_role_screen.name)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Mostrar el popup si hay un error
        if (state.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { /* Ignorar o hacer alguna acción si se quiere cerrar el popup */ },
                confirmButton = {
                    Button(onClick = { viewModel.resetState() }) {
                        Text("OK")
                    }
                },
                title = { Text("Error de inicio de sesión") },
                text = { Text(state.errorMessage ?: "Ha ocurrido un error desconocido.") }
            )
        }
    }
}