package com.example.aventurape_androidmobile.userProfileManagement.screens.states

import com.example.aventurape_androidmobile.userProfileManagement.models.ProfileA
import com.example.aventurape_androidmobile.userProfileManagement.models.ProfileE

data class ProfileStateE (
    val email: String="",
    val street: String="",
    val number: String="",
    val city: String="",
    val postalCode: String="",
    val country: String="",
    val name: String="",
    val profileECompleted: ProfileE = ProfileE(),
    val profileECompletedSucces: Boolean = false,
    val profileECompletedError: String? = null
)