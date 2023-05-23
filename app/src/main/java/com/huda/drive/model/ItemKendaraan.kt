package com.huda.drive.model

class ItemKendaraan {
    lateinit var name: String
    lateinit var img: String
    lateinit var merk: String
    var price: Int? = null
    lateinit var features: String

    constructor()

    constructor(
        name: String,
        img: String,
        merk: String,
        price: Int,
        features: String
    ) {
        this.name = name
        this.img = img
        this.merk = merk
        this.price = price
        this.features = features
    }
}