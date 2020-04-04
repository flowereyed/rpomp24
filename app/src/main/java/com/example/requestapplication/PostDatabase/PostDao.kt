package com.example.requestapplication.PostDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll(): Flowable<List<PostEntity>>

    @Query("SELECT COUNT(identifier) FROM post")
    fun getCount(): Flowable<Int>

    @Insert
    fun insert(post: PostEntity)

    @Delete
    fun delete(post: PostEntity)
}