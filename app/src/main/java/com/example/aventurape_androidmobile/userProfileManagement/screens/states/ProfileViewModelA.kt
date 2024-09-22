package com.example.aventurape_androidmobile.userProfileManagement.screens.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aventurape_androidmobile.userProfileManagement.models.ProfileA
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileA

class ProfileViewModelA(): ViewModel() {
    var state by mutableStateOf(ProfileStateA())
        private set
    var profileACompleted: ProfileA = ProfileA()

    fun inputProfileData(firstName: String,lastName: String, email: String, street: String, number: String, city: String, postalCode: String, country: String, gender: String) {
        state = state.copy(firstName=firstName, lastName=lastName, email=email, street=street, number=number, city=city, postalCode=postalCode, country=country,gender=gender)
    }
    fun resetState(){
        state= ProfileStateA()
    }
    suspend fun completeProfile(firstName: String, lastName: String, email: String,street:String, number: String, city:String, postalCode:String,country:String, gender: String, token: String){
        val request= UserRequestProfileA(firstName=firstName, lastName=lastName, email=email, street=street, number=number, city=city, postalCode=postalCode, country=country, gender=gender)
        val response= RetrofitClient.placeholder.saveProfileA(request,"Bearer $token")
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
}