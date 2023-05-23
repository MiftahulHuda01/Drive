package com.huda.drive.model

class Users {
    lateinit var uid: String
    lateinit var username: String
    lateinit var phone: String
    lateinit var email: String
    lateinit var profile: String
    lateinit var password: String
    lateinit var role: String

    constructor()

    constructor(
        uid: String,
        username: String,
        phone: String,
        email: String,
        profile: String,
        password: String,
        role: String
    ) {
        this.uid = uid
        this.username = username
        this.phone = phone
        this.email = email
        this.profile = profile
        this.password = password
        this.role = role
    }
}