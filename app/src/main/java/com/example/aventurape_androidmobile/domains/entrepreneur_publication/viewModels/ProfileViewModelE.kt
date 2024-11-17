package com.example.aventurape_androidmobile.domains.entrepreneur_publication.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.states.ProfileStateA
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.models.ProfileE
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.states.ProfileStateE
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileE
import com.example.aventurape_androidmobile.utils.models.UserResponseProfileA
import com.example.aventurape_androidmobile.utils.models.UserResponseProfileE
import kotlinx.coroutines.launch

class ProfileViewModelE(): ViewModel() {
    var state by mutableStateOf(ProfileStateE())
        private set
    var profileECompleted: ProfileE = ProfileE()
    var profileExists by mutableStateOf(false)

    fun checkProfileExists(userId: Long) {
        viewModelScope.launch {
            val response = RetrofitClient.placeholder.getProfileByUserIdE(userId)
            if (response.isSuccessful && response.body() != null) {
                state = mapUserResponseToProfileState(response.body()!!)
                profileExists = true
            } else {
                profileExists = false
            }
        }
    }

    fun inputProfileData(
        nameEntrepreneurship: String,
        addressCity: String,
        addressCountry: String,
        addressNumber: String,
        addressPostalCode: String,
        addressStreet: String,
        emailAddress: String)
    {
        state = state.copy(
            nameEntrepreneurship=nameEntrepreneurship,
            addressCity=addressCity,
            addressCountry=addressCountry,
            addressNumber=addressNumber,
            addressPostalCode=addressPostalCode,
            addressStreet=addressStreet,
            emailAddress=emailAddress)
    }
    fun resetState(){
        state= ProfileStateE()
    }
    suspend fun completeProfileE(nameEntrepreneurship: String,
                                 addressCity: String,
                                 addressCountry: String,
                                 addressNumber: String,
                                 addressPostalCode: String,
                                 addressStreet: String,
                                 emailAddress: String)
    {
        val requestE= UserRequestProfileE(
            nameEntrepreneurship=nameEntrepreneurship,
            addressCity=addressCity,
            addressCountry=addressCountry,
            addressNumber=addressNumber,
            addressPostalCode=addressPostalCode,
            addressStreet=addressStreet,
            emailAddress=emailAddress)

        val responseE= RetrofitClient.placeholder.saveProfileE(requestE)
        if(responseE.isSuccessful && responseE.body()!=null){
            val profileResponse= responseE.body()!!
            val profileECompleted= ProfileE(
                name = profileResponse.name,
                city = profileResponse.city,
                country = profileResponse.country,
                number = profileResponse.number,
                postalCode = profileResponse.postalCode,
                streetAddress = profileResponse.streetAddress,
                email = profileResponse.emailAddress,

                )
            state= state.copy(profileECompletedSucces = true, profileECompleted= profileECompleted, profileECompletedError= null)
        }else {
            state = state.copy(
                profileECompletedSucces = false,
                profileECompletedError = "Error al completar el perfil."
            )
        }
    }
    private fun mapUserResponseToProfileState(userResponse: UserResponseProfileE): ProfileStateE {
        return ProfileStateE(
            nameEntrepreneurship = userResponse.name,
            addressCity = userResponse.city,
            addressCountry = userResponse.country,
            addressNumber = userResponse.number,
            addressPostalCode = userResponse.postalCode,
            addressStreet = userResponse.streetAddress,
            emailAddress = userResponse.emailAddress,
        )
    }
}