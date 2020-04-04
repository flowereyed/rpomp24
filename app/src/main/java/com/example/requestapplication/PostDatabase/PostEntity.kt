package com.example.requestapplication.PostDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey var primaryId: Int,
    @ColumnInfo(name = "lat") var lat: String,
    @ColumnInfo(name = "lon") var lon: String,
    @ColumnInfo(name = "identifier") var identifier: String,
    @ColumnInfo(name = "caption") var caption: String,
    @ColumnInfo(name = "image") var image: String
)