package com.example.aventurape_androidmobile.utils.interfaces

import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import com.example.aventurape_androidmobile.Beans.Comment
import com.example.aventurape_androidmobile.Beans.Review
import com.example.aventurape_androidmobile.utils.models.UserRequestSignIn
import com.example.aventurape_androidmobile.utils.models.UserRequestSignUp
import com.example.aventurape_androidmobile.utils.models.UserResponse
import com.example.aventurape_androidmobile.utils.models.UserRolesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface Placeholder {
    @POST("authentication/sign-up")
    suspend fun singUp(@Body request: UserRequestSignUp): Response<UserResponse>

    @POST("authentication/sign-in")
    suspend fun singIn(@Body request: UserRequestSignIn): Response<UserResponse>

    @GET("publication/all-publications")
    suspend fun getAllAdventures(): Response<List<Adventure>>

    @POST("publication/{publicationId}/add-comment")
    suspend fun sendReview(
        @Path("publicationId") publicationId: Long,
        @Body review: Review
    ): Response<Void>

    @GET("publication/{publicationId}/comments")
    suspend fun getComments(
        @Path("publicationId") publicationId: Long
    ): Response<List<Comment>>


    @POST("publication/create-publication")
    suspend fun sendPublication(
        @Header("Authorization") token: String,
        @Body publication: PublicationRequest
    ): Response<Void>

    @GET("publication/{entrepreneurId}/publications")
    suspend fun getPublications(
        @Header("Authorization") token: String,
        @Path("entrepreneurId") entrepreneurId: Long
    ): Response<List<PublicationResponse>>

    @GET("users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: Long
    ): Response<UserRolesResponse>
}