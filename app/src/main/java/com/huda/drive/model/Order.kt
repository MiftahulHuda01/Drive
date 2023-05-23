package com.huda.drive.model

data class Order(
    val id: String,

    val uid: String,
    val username: String,
    val phone: String,
    val email: String,
    val profile: String?,

    val timeRental: String,
    val timeReturn: String,
    val day: Int,
    val timestamp: Long,
    val item: Kendaraan,
    val price: Int,
    val status: Int
)