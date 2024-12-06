package com.example.diyapp.data

import android.content.Context
import android.widget.Toast
import com.example.diyapp.R
import javax.inject.Inject

class SessionManager @Inject constructor(){
    private val PREF_NAME = "AppPreferences"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"
    private val KEY_EMAIL = "email"
    private val KEY_NAME = "name"
    private val KEY_LASTNAME = "lastname"
    private val KEY_PASSWORD = "password"
    private val KEY_PHOTO = "photo"

    fun isUserLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setUserLoggedIn(
        context: Context,
        loggedIn: Boolean,
        email: String = "",
        name: String = "",
        lastname: String = "",
        password: String = "",
        photo: String = ""
    ) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, loggedIn)
            putString(KEY_EMAIL, email)
            putString(KEY_NAME, name)
            putString(KEY_LASTNAME, lastname)
            putString(KEY_PASSWORD, password)
            putString(KEY_PHOTO, photo)
            apply()
        }
    }

    fun getUserInfo(context: Context): Map<String, String> {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return mapOf(
            "email" to (sharedPref.getString(KEY_EMAIL, "") ?: ""),
            "name" to (sharedPref.getString(KEY_NAME, "") ?: ""),
            "lastname" to (sharedPref.getString(KEY_LASTNAME, "") ?: ""),
            "password" to (sharedPref.getString(KEY_PASSWORD, "") ?: ""),
            "photo" to (sharedPref.getString(KEY_PHOTO, "") ?: "")
        )
    }

    fun showToast(context: Context, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun validateInputs(context: Context, email: String, password: String): Boolean {
        val isEmailValid = isValidEmail(email)
        val isPasswordValid = isValidPassword(password)

        if (!isEmailValid) {
            showToast(context, R.string.checkEmail)
            return false
        }
        if (!isPasswordValid) {
            showToast(context, R.string.checkPassword)
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        val passwordPattern =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
        return password.matches(passwordPattern.toRegex())
    }
}
