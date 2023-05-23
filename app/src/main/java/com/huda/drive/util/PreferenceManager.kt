package com.huda.drive.util

import android.content.Context
import com.google.gson.Gson
import com.huda.drive.model.User

object PreferenceManager {
    private const val PREF_NAME = "MyAppPrefs"
    private const val KEY_USER = "user"

    fun saveUser(context: Context, user: User) {
        val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val gson = Gson()
        val userJson = gson.toJson(user)
        editor.putString(KEY_USER, userJson)
        editor.apply()
    }

    fun getUser(context: Context): User? {
        val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userJson = sharedPrefs.getString(KEY_USER, null)
        val gson = Gson()
        return gson.fromJson(userJson, User::class.java)
    }

    fun deleteUser(context: Context) {
        val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.clear().apply()
    }
}