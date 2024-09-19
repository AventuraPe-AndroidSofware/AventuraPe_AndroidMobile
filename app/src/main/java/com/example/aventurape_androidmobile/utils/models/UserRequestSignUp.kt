package com.example.aventurape_androidmobile.utils.models

data class UserRequestSignUp(
    val username: String = "",
    val password: String = "",
    val roles: List<String> = listOf()
)
