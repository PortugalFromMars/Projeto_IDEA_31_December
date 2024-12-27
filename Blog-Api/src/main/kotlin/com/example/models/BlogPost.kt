package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class BlogPost(
    val id: String? = null,
    val title: String,
    val content: String,
    val authorId: String,
    val likes: MutableList<String> = mutableListOf(), // Store user IDs who liked the post
    val timestamp: Long = System.currentTimeMillis()
)
