package com.huda.drive.model

class Orders {
    lateinit var id: String
    lateinit var uid: String
    lateinit var username: String
    lateinit var phone: String
    lateinit var email: String
    lateinit var profile: String
    lateinit var timeRental: String
    lateinit var timeReturn: String
    var day: Int? = null
    var timestamp: Long? = null
    lateinit var item: ItemKendaraan
    var price: Int? = null
    var status: Int? = null


    constructor()

    constructor(
        id: String,
        uid: String,
        username: String,
        phone: String,
        email: String,
        profile: String,
        timeRental: String,
        timeReturn: String,
        day: Int,
        timestamp: Long?,
        item: ItemKendaraan,
        price: Int,
        status: Int,
    ) {
        this.id = id
        this.uid = uid
        this.username = username
        this.phone = phone
        this.email = email
        this.profile = profile
        this.timeRental = timeRental
        this.timeReturn = timeReturn
        this.day = day
        this.timestamp = timestamp
        this.item = item
        this.price = price
        this.status = status
    }
}