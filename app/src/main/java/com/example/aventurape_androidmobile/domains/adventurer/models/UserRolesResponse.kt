package com.example.aventurape_androidmobile.domains.adventurer.models

data class UserRolesResponse(
    val id: Long,
    val username: String,
    val roles: List<String>
)