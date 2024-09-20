package com.example.aventurape_androidmobile.models

import com.example.aventurape_androidmobile.Interface.Placeholder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val placeHolder: Placeholder by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Placeholder::class.java)
    }
}