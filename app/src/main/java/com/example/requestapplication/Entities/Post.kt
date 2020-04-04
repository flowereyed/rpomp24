package com.example.requestapplication.Entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,
    val identifier: String,
    val caption: String,
    val image: String
) : Parcelable