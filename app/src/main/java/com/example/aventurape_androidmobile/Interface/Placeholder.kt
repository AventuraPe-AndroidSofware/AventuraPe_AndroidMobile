package com.example.aventurape_androidmobile.Interface

import com.example.aventurape_androidmobile.Beans.Profile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Placeholder {
    //@GET ("profiles/adventurer/{profileId})")
    @GET ("profiles/{profileId})")
    suspend fun getProfiles(@Path("profileId") profileId: String): Response<List<Profile>>
}