package com.example.aventurape_androidmobile.domains.entrepreneur_publication.models

class ProfileE (
    val id: Long=0,
    val userId: Long=0,
    val name: String="",
    val city: String="",
    val country: String="",
    val number: String="",
    val postalCode: String="",
    val streetAddress: String="",
    val email: String="",
    val imageUrl: String = "https://randomuser.me/api/portraits/men/${id}.jpg" // Genera una URL única basada en el ID
)