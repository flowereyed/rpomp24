package com.example.requestapplication

import com.example.requestapplication.Entities.Post
import io.reactivex.Single
import retrofit2.http.GET

interface Request {

    @GET("EPIC/api/natural/date/2015-10-31?api_key=DEMO_KEY")
    fun getAllPosts(): Single<ArrayList<Post>>
}