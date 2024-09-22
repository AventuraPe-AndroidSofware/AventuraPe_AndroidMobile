package com.example.aventurape_androidmobile.userProfileManagement.screens.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aventurape_androidmobile.userProfileManagement.models.ProfileA
import com.example.aventurape_androidmobile.userProfileManagement.models.ProfileE
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileA
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileE

class ProfileViewModelE(): ViewModel() {
    var state by mutableStateOf(ProfileStateE())
        private set
    var profileECompleted: ProfileE = ProfileE()

    fun inputProfileData(email: String, street: String, number: String, city: String, postalCode: String, country: String, name: String) {
        state = state.copy(email=email, street=street, number=number, city=city, postalCode=postalCode, country=country, name=name)
    }
    fun resetState(){
        state= ProfileStateE()
    }
    suspend fun completeProfileE(email: String, street: String, number: String, city: String, postalCode: String, country: String, name: String, token:String){
        val requestE= UserRequestProfileE(email=email, street=street, number=number, city=city, postalCode=postalCode, country=country, name=name)
        val responseE= RetrofitClient.placeholder.saveProfileE(requestE, token)
        if(responseE.isSuccessful && responseE.body()!=null){
            val profileResponse= responseE.body()!!
            val profileACompleted= ProfileE(
                name = profileResponse.name,
                email= profileResponse.email,
                streetAddress = profileResponse.streetAddress
            )
            state= state.copy(profileECompletedSucces = true, profileECompleted= profileECompleted, profileECompletedError= null)
        }else {
            state = state.copy(
                profileECompletedSucces = false,
                profileECompletedError = "Error al completar el perfil."
            )
        }
    }
}