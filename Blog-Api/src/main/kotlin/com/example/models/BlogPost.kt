package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class BlogPost(
    val id: String? = null, // Nullable for when you create a new post (Firebase will generate the ID)
    val title: String,
    val content: String,
    val authorId: String, // ID of the user who created the post
    val timestamp: Long = System.currentTimeMillis() // Timestamp for sorting
)