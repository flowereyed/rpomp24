package com.example.requestapplication.PostDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(PostEntity::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}