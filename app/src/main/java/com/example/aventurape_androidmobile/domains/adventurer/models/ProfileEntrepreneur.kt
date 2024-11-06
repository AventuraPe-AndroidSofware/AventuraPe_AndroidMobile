package com.example.aventurape_androidmobile.domains.adventurer.models

data class ProfileEntrepreneur(
    val id: Int,
    val userId: Int,
    val name: String,
    val email: String,
    val streetAddress: String,
    val imageUrl: String = "https://randomuser.me/api/portraits/men/${id}.jpg" // Genera una URL única basada en el ID
)
