package com.example.aventurape_androidmobile.utils


import com.example.aventurape_androidmobile.utils.interfaces.Placeholder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    //aqui coloquen la url base de su api
    private const val BASE_URL = "http://10.0.2.2:8090/api/v1/"
    private val gson: Gson = GsonBuilder().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val placeholder: Placeholder = retrofit.create(Placeholder::class.java)
}