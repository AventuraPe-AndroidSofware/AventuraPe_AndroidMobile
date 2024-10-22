package com.example.aventurape_androidmobile.domains.adventurer.states

import com.example.aventurape_androidmobile.domains.adventurer.models.ProfileA

data class ProfileStateA (
    val firstName: String="",
    val lastName: String="",
    val email: String="",
    val street: String="",
    val number: String="",
    val city: String="",
    val postalCode: String="",
    val country: String="",
    val gender: String="",
    val profileACompleted: ProfileA = ProfileA(),
    val profileCompletedSucces: Boolean = false,
    val profileCompletedError: String? = null
)