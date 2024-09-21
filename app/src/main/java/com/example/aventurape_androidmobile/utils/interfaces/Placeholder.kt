package com.example.aventurape_androidmobile.utils.interfaces

import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import com.example.aventurape_androidmobile.utils.models.UserRequestSignIn
import com.example.aventurape_androidmobile.utils.models.UserRequestSignUp
import com.example.aventurape_androidmobile.utils.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface Placeholder {
    @POST("authentication/sign-up")
    suspend fun singUp(
        @Body request: UserRequestSignUp): Response<UserResponse>

    @POST("authentication/sign-in")
    suspend fun singIn(@Body request: UserRequestSignIn): Response<UserResponse>

    @GET("publication/all-publications")
    suspend fun getAllAdventures(
        @Header("Authorization") token: String
    ): Response<List<Adventure>>

    @POST("publication/create-publication")
    suspend fun sendPublication(
        @Header("Authorization") token: String,
        @Path("entrepreneurId") entrepreneurId: Long,
        @Body publication: PublicationRequest
    ): Response<Void>

    @GET("publication/{entrepreneurId}/publications")
    suspend fun getPublications(
        @Header("Authorization") token: String,
        @Path("entrepreneurId") entrepreneurId: Long
    ): Response<List<PublicationResponse>>

}