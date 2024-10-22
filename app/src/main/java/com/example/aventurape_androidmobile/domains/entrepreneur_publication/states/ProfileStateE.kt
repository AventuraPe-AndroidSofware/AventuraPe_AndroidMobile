package com.example.aventurape_androidmobile.domains.entrepreneur_publication.states

import com.example.aventurape_androidmobile.domains.entrepreneur_publication.models.ProfileE

data class ProfileStateE (
    val nameEntrepreneurship: String="",
    val addressCity: String="",
    val addressCountry: String="",
    val addressNumber: String="",
    val addressPostalCode: String="",
    val addressStreet: String="",
    val emailAddress: String="",
    val profileECompleted: ProfileE = ProfileE(),
    val profileECompletedSucces: Boolean = false,
    val profileECompletedError: String? = null

)