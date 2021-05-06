package com.eniola.addressbookapp.repository.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 05-May-2021
 */

@Parcelize
@Entity
data class Contact(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Long? = null
}