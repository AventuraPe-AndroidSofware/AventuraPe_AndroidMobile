package com.example.aventurape_androidmobile.domains.authentication.screens.states

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aventurape_androidmobile.domains.authentication.models.UserLogged
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRequestSignIn
import com.example.aventurape_androidmobile.utils.models.UserRequestSignUp

class LoginViewModel() : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    var userLogged: UserLogged = UserLogged()

    fun inputCredentials(username: String, password: String) {
        state = state.copy(username = username, password = password)
    }

    fun resetState() {
        state = LoginState()
    }

    suspend fun signInUser(username: String, password: String) {
        val request = UserRequestSignIn(username = username, password = password)
        val response = RetrofitClient.placeholder.singIn(request)
        if (response.isSuccessful && response.body() != null) {
            val userResponse = response.body()!!
            val userLogged = UserLogged(
                id = userResponse.id,
                username = userResponse.username,
                token = userResponse.token
            )
            // Actualizar el token en el RetrofitClient
            userLogged.token?.let { RetrofitClient.updateToken(it) }

            state = state.copy(loginSuccess = true, userLogged = userLogged, errorMessage = null)
        } else {
            state = state.copy(loginSuccess = false, errorMessage = "Usuario o contraseña incorrectos.")
        }
    }
}