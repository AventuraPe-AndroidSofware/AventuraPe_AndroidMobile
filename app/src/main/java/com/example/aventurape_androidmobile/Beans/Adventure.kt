package com.example.aventurape_androidmobile.Beans

data class Adventure(
    val id: Long,
    val entrepreneurId: Long,
    val nameActivity: String,
    val description: String,
    val timeDuration: Long,
    val cantPeople: Int,
    val cost: Float,
    val image: String

)