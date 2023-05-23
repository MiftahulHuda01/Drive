package com.huda.drive.model

data class User(
    val uid: String,
    val username: String,
    val phone: String,
    val email: String,
    val password: String,
    val profile: String?,
    val role: String
)