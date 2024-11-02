package com.example.aventurape_androidmobile.domains.adventurer.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.models.ProfileA
import com.example.aventurape_androidmobile.domains.adventurer.states.ProfileStateA
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileA
import com.example.aventurape_androidmobile.utils.models.UserResponseProfileA
import kotlinx.coroutines.launch

class ProfileViewModelA(): ViewModel() {
    var state by mutableStateOf(ProfileStateA())
        private set
    var profileACompleted: ProfileA = ProfileA()

    var profileExists by mutableStateOf(false)

    fun checkProfileExists(userId: Long) {
        viewModelScope.launch {
            val response = RetrofitClient.placeholder.getProfileByUserIdA(userId)
            if (response.isSuccessful && response.body() != null) {
                state = mapUserResponseToProfileState(response.body()!!)
                profileExists = true
            } else {
                profileExists = false
            }
        }
    }

    fun inputProfileData(firstName: String,lastName: String, email: String, street: String, number: String, city: String, postalCode: String, country: String, gender: String) {
        state = state.copy(firstName=firstName, lastName=lastName, email=email, street=street, number=number, city=city, postalCode=postalCode, country=country,gender=gender)
    }
    fun resetState(){
        state= ProfileStateA()
    }
    suspend fun completeProfile(firstName: String, lastName: String, email: String,street:String, number: String, city:String, postalCode:String,country:String, gender: String){
        val request= UserRequestProfileA(firstName=firstName, lastName=lastName, email=email, street=street, number=number, city=city, postalCode=postalCode, country=country, gender=gender)
        val response= RetrofitClient.placeholder.saveProfileA(request)
        if(response.isSuccessful && response.body()!=null){
            val profileResponse= response.body()!!
            val profileACompleted= ProfileA(
                fullName = profileResponse.fullName,
                gender= profileResponse.gender,
                email= profileResponse.email,
                streetAddress = profileResponse.streetAddress
            )
            state= state.copy(profileCompletedSucces= true, profileACompleted= profileACompleted, profileCompletedError= null)
        }else {
            state = state.copy(
                profileCompletedSucces = false,
                profileCompletedError = "Error al completar el perfil."
            )
        }
    }
    private fun mapUserResponseToProfileState(userResponse: UserResponseProfileA): ProfileStateA {
        return ProfileStateA(
            firstName = userResponse.fullName,
            email = userResponse.email,
            gender = userResponse.gender,
            street = userResponse.streetAddress
        )
    }
}