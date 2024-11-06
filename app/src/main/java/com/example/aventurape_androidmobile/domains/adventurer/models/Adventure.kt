package com.example.aventurape_androidmobile.domains.adventurer.models

data class Adventure(
    val Id: Long,
    val entrepreneurId: Long,
    val nameActivity: String,
    val description: String,
    val timeDuration: Int,
    val image: String,
    val cantPeople: Int,
    val cost: Double
)