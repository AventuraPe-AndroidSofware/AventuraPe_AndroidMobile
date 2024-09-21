package com.example.aventurape_androidmobile.utils.interfaces

import com.example.aventurape_androidmobile.utils.models.UserRequestProfileA
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileE
import com.example.aventurape_androidmobile.utils.models.UserRequestSignIn
import com.example.aventurape_androidmobile.utils.models.UserRequestSignUp
import com.example.aventurape_androidmobile.utils.models.UserResponse
import com.example.aventurape_androidmobile.utils.models.UserResponseProfileA
import com.example.aventurape_androidmobile.utils.models.UserResponseProfileE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Placeholder {
    @POST("authentication/sign-up")
    suspend fun singUp(@Body request: UserRequestSignUp):Response<UserResponse>

    @POST("authentication/sign-in")
    suspend fun singIn(@Body request: UserRequestSignIn):Response<UserResponse>

    @POST("profiles/adventurer/{profileId}")
    suspend fun saveProfileA(@Body request: UserRequestProfileA):Response<UserResponseProfileA>
    @POST("profiles/entrepreneur/{profileId}")
    suspend fun saveProfileE(@Body request: UserRequestProfileE):Response<UserResponseProfileE>
}