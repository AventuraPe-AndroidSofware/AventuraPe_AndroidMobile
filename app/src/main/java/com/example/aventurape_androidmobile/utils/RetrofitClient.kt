package com.example.aventurape_androidmobile.utils


import com.example.aventurape_androidmobile.domains.authentication.models.AuthInterceptor
import com.example.aventurape_androidmobile.utils.interfaces.Placeholder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
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

    // Agregar el interceptor para la autenticación con token
    fun create(token: String): Placeholder {
        val authInterceptor = AuthInterceptor(token)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // Incluye el cliente con el interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(Placeholder::class.java)
    }
}