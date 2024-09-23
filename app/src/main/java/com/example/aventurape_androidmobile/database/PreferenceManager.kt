import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object PreferenceManager {

    private const val PREFERENCES_FILE_KEY = "com.example.aventurape_androidmobile.PREFERENCE_FILE_KEY"
    private const val USER_ID_KEY = "user_id"
    private const val USERNAME_KEY = "username"
    private const val TOKEN_KEY = "token"
    private const val ROLES_KEY = "roles"

    // Función para guardar la información del usuario
    fun saveUser(context: Context, userId: Long, username: String, token: String, roles: List<String>) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            // Sobrescribe los valores guardados previamente
            putLong(USER_ID_KEY, userId)
            putString(USERNAME_KEY, username)
            putString(TOKEN_KEY, token)
            putStringSet(ROLES_KEY, roles.toSet()) // Guarda los roles como un Set
            commit() // Guarda de manera síncrona
        }
        Log.d("SharedPreferences", "Data saved: userId=$userId, username=$username, token=$token, roles=$roles")
    }

    // Función para obtener la información del usuario
    fun getUserRoles(context: Context): List<String>? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet(ROLES_KEY, emptySet())?.toList()
    }

    fun getUserId(context: Context): Long {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(USER_ID_KEY, -1)
    }

    fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USERNAME_KEY, null)
    }

    fun getToken(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    // Función para borrar los datos del usuario (opcional si quieres eliminar de forma explícita)
    fun clearUser(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear() // Elimina todos los datos guardados en SharedPreferences
            apply()
        }
    }

    fun getAllPreferences(context: Context): Map<String, *> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.all // Devuelve todos los valores guardados
    }
}