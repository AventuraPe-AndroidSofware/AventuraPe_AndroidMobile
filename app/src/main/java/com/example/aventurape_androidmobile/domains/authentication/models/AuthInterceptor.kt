package com.example.aventurape_androidmobile.domains.authentication.models

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithToken = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")  // Agrega el token como Bearer
            .build()
        return chain.proceed(requestWithToken)
    }
}
