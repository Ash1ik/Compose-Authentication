package org.example.project

// commonMain
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

object SessionManager {
    private val settings = Settings() // No platform setup needed

    fun login(name: String, email: String,token: String) {
        settings["is_logged_in"] = true
        settings["user_name"] = name
        settings["user_email"] = email
        settings["auth_token"] = token
    }

    fun isLoggedIn(): Boolean = settings.getBoolean("is_logged_in", false)
    fun getAuthToken(): String = settings.getString("auth_token", "")
    fun getUserName(): String = settings.getString("user_name", "")
    fun getUserEmail(): String = settings.getString("user_email", "")

    fun logout() = settings.clear()
}
