package com.example.aventurape_androidmobile.utils


import com.example.aventurape_androidmobile.utils.interfaces.Placeholder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    // Variable que contiene el token
    private var token: String? = null

    // Método para actualizar el token
    fun updateToken(newToken: String) {
        token = newToken
    }

    //Metodo para obtener el token
    fun  getToken(): String? {
        return this.token;
    }

    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    // Cliente OkHttp que usa el TokenInterceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor { token }) // Interceptor para agregar el token
        .build()

    private val gson = GsonBuilder().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient) // Asigna el cliente OkHttp modificado
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val placeholder: Placeholder = retrofit.create(Placeholder::class.java)
}