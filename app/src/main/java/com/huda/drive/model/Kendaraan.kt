package com.huda.drive.model

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable

data class Kendaraan(
    val id: Int,
    val name: String?,
    val img: String?,
    val merk: String?,
    val price: Int,
    val features: String?
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(img)
        parcel.writeString(merk)
        parcel.writeInt(price)
        parcel.writeString(features)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Kendaraan> {
        override fun createFromParcel(parcel: Parcel): Kendaraan {
            return Kendaraan(parcel)
        }

        override fun newArray(size: Int): Array<Kendaraan?> {
            return arrayOfNulls(size)
        }
    }

}