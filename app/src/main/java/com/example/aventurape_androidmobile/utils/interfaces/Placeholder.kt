package com.example.aventurape_androidmobile.utils.interfaces

import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import com.example.aventurape_androidmobile.domains.adventurer.models.Comment
import com.example.aventurape_androidmobile.domains.adventurer.models.Review
import com.example.aventurape_androidmobile.utils.models.ProfileEntrepreneurResponse
import com.example.aventurape_androidmobile.utils.models.FavoritePublicationRequest
import com.example.aventurape_androidmobile.utils.models.FavoritePublicationResponse
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileA
import com.example.aventurape_androidmobile.utils.models.UserRequestProfileE
import com.example.aventurape_androidmobile.utils.models.UserRequestSignIn
import com.example.aventurape_androidmobile.utils.models.UserRequestSignUp
import com.example.aventurape_androidmobile.utils.models.UserResponse
import com.example.aventurape_androidmobile.utils.models.UserResponseProfileA
import com.example.aventurape_androidmobile.utils.models.UserResponseProfileE
import com.example.aventurape_androidmobile.utils.models.UserRolesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Placeholder {
    @POST("authentication/sign-up")
    suspend fun singUp(@Body request: UserRequestSignUp): Response<UserResponse>

    @POST("authentication/sign-in")
    suspend fun singIn(@Body request: UserRequestSignIn): Response<UserResponse>

    //TODAS LAS PUBLICACIONES
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
        @Body publication: PublicationRequest
    ): Response<Void>

    //EDITAR
    @PUT("publication/{publicationId}/update-publication")
    suspend fun updatePublication(
        @Path("publicationId") publicationId: Long,
        @Body publication: PublicationRequest
    ): Response<Void>

//agregar una aventura a favoritos
    @POST("favorite-publications/create-favorite-publication")
    suspend fun addFavoritePublication(
        @Body request: FavoritePublicationRequest
    ): Response<FavoritePublicationResponse>
    @GET("favorite-publications/getFavoritePublicationByProfileId/{profileId}")
        suspend fun getFavoritePublicationByProfileId(
            @Path("profileId") profileId: Long
        ): Response<List<FavoritePublicationResponse>>

        //Eliminar publicación de favoritos
        @DELETE("favorite-publications/delete-favorite-publication/{favoritePublicationId}")
        suspend fun deleteFavoritePublication(
            @Path("favoritePublicationId") favoritePublicationId: Long
        ): Response<Unit>

    //Eliminar publicación /api/v1/publication/{publicationId}/delete-publication
    @DELETE("publication/{publicationId}/delete-publication")
    suspend fun deletePublication(
        @Path("publicationId") publicationId: Long
    ): Response<Unit>

    //ESTADISTICAS EL ENTREPRENEUR


    @GET("publication/{publicationId}")
    suspend fun getAdventureById(
        @Path("publicationId") publicationId: Long
    ): Response<Adventure>

    @GET("publication/{entrepreneurId}/publications")
    suspend fun getPublications(
        @Path("entrepreneurId") entrepreneurId: Long
    ): Response<List<PublicationResponse>>

    @GET("users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: Long
    ): Response<UserRolesResponse>

    @POST("profiles/adventurer")
    suspend fun saveProfileA(@Body request: UserRequestProfileA):Response<UserResponseProfileA>

    @POST("profiles/entrepreneur")
    suspend fun saveProfileE(@Body request: UserRequestProfileE):Response<UserResponseProfileE>

    @GET("profiles/entrepreneur")
    suspend fun getAllProfilesEntrepreneur():Response<List<ProfileEntrepreneurResponse>>
    @GET("profiles/adventurer/user/{userId}")
    suspend fun getProfileByUserIdA(@Path("userId") userId: Long): Response<UserResponseProfileA>

    @GET("profiles/entrepreneur/user/{userId}")
    suspend fun getProfileByUserIdE(@Path("userId") userId: Long): Response<UserResponseProfileE>
    //ALL USERS
    @GET("users")
    suspend fun getUsers(): Response<List<UserRolesResponse>>
}