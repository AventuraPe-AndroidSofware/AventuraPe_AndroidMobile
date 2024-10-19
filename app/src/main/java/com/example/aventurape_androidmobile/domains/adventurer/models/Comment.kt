package com.example.aventurape_androidmobile.domains.adventurer.models

data class Comment(
    val id: Long,
    val adventureId: Long,
    val content: String,
    val rating: Int
)