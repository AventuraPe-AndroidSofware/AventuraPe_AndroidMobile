package com.example.aventurape_androidmobile.database

import android.content.Context
import android.content.SharedPreferences

fun saveToken(context: Context, token: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("auth_token", token)
    editor.apply()
}

fun getToken(context: Context): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("auth_token", null)
}

fun saveUserRole(context: Context, role: String) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("user_role", role)
    editor.apply() // Guarda los cambios de forma asincrónica
}