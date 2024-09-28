package com.example.aventurape_androidmobile.domains.adventurer.models

data class Adventure(
    val Id: Long,
    val nameActivity: String,
    val description: String,
    val timeDuration: String,
    val cantPeople: Int,
    val cost: Int,
    val image: String
)