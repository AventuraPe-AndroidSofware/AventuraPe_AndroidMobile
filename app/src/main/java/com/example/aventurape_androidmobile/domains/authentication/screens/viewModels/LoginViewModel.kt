package com.example.aventurape_androidmobile.domains.authentication.screens.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aventurape_androidmobile.domains.authentication.models.UserLogged
import com.example.aventurape_androidmobile.domains.authentication.screens.states.LoginState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRequestSignIn

//Por si se habilita un cerrar sesion llamar a "PreferenceManager.clearUser(context)"
class LoginViewModel : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    var userLogged: UserLogged = UserLogged()

    fun inputCredentials(username: String, password: String) {
        state = state.copy(username = username, password = password)
    }

    fun resetState() {
        state = LoginState()
    }

    suspend fun signInUser(context: Context, username: String, password: String) {
        Log.d("LoginViewModel", "Intentando iniciar sesión para el usuario: $username")
        val request = UserRequestSignIn(username = username, password = password)
        val response = RetrofitClient.placeholder.singIn(request)
        if (response.isSuccessful && response.body() != null) {
            Log.d("LoginViewModel", "Inicio de sesión exitoso: ${response.body()}")
            val userResponse = response.body()!!
            val userLogged = UserLogged(
                id = userResponse.id,
                username = userResponse.username,
                token = userResponse.token
            )

            // Actualizar el token en el RetrofitClient
            userLogged.token?.let { RetrofitClient.updateToken(it) }

            // Guardar datos del usuario en SharedPreferences
            saveUserInSharedPreferences(context, userLogged.id!!)
            state = state.copy(loginSuccess = true, userLogged = userLogged, errorMessage = null)
            Log.d("LoginViewModel", "Estado actualizado a loginSuccess=true")
        } else {
            Log.e("LoginViewModel", "Fallo en el inicio de sesión: Código ${response.code()}")
            state = state.copy(loginSuccess = false, errorMessage = "Usuario o contraseña incorrectos.")
        }
    }

    private suspend fun saveUserInSharedPreferences(context: Context, userId: Long) {
        Log.d("LoginViewModel", "Intentando obtener datos de usuario con ID: $userId")
        val response = RetrofitClient.placeholder.getUserById(userId)
        if (response.isSuccessful && response.body() != null) {
            Log.d("LoginViewModel", "Obtención de usuario exitosa: ${response.body()}")
            val userRolesResponse = response.body()!!

            // Asegurarse de que los roles no sean nulos
            val roles = userRolesResponse.roles ?: listOf()
            val userLogged = UserLogged(
                id = userRolesResponse.id,
                username = userRolesResponse.username,
                token = RetrofitClient.getToken(),
                roles = roles
            )

            // Guardar los datos del usuario en SharedPreferences
            Log.d("LoginViewModel", "Guardando usuario en SharedPreferences: id=${userLogged.id}, username=${userLogged.username}, token=${userLogged.token}, roles=${userLogged.roles}")
            PreferenceManager.saveUser(
                context = context,
                userId = userLogged.id ?: -1,
                username = userLogged.username ?: "",
                token = userLogged.token ?: "",
                roles = userLogged.roles ?: listOf()
            )
            Log.d("LoginViewModel", "Usuario guardado correctamente en SharedPreferences")
        } else {
            Log.e("LoginViewModel", "Fallo al obtener datos del usuario: Código ${response.code()}")
        }
    }
}