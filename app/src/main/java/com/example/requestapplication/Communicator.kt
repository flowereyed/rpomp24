package com.example.requestapplication

import com.example.requestapplication.Entities.Post

interface Communicator {
    fun passData(post: Post)
}