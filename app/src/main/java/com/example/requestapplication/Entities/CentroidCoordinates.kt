package com.example.requestapplication.Entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CentroidCoordinates(
    val lat: String,
    val lon: String
) : Parcelable