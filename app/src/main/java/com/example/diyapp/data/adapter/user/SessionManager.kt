package com.example.diyapp.data.adapter.user

import android.content.Context
import android.widget.Toast

object SessionManager {
    private const val PREF_NAME = "AppPreferences"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_EMAIL = "email"
    private const val KEY_NAME = "name"
    private const val KEY_LASTNAME = "lastname"
    private const val KEY_PHOTO = "photo"

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
        photo: String = ""
    ) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, loggedIn)
            putString(KEY_EMAIL, email)
            putString(KEY_NAME, name)
            putString(KEY_LASTNAME, lastname)
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
            "photo" to (sharedPref.getString(KEY_PHOTO, "") ?: "")
        )
    }

    fun showToast(context: Context, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
