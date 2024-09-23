package com.example.aventurape_androidmobile.utils.models

data class UserRolesResponse(
    val id: Long? = null,
    val username: String? = "",
    val roles:List<String>? = listOf()
)