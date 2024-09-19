package com.example.aventurape_androidmobile.domains.authentication.screens.states

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.example.aventurape_androidmobile.domains.authentication.models.UserLogged

data class LoginState (
    val username: String = "",
    val password: String = "",
    val userLogged: UserLogged = UserLogged(),
    val loginSuccess: Boolean = false,
    val errorMessage: String? = null // Dejarlo en null cuando no hay error
)