package com.example.models // Correct package

import kotlinx.serialization.Serializable


@Serializable
data class Comment(
    val id: String? = null, // Nullable for new comments
    val postId: String,  // ID of the post this comment belongs to
    val authorId: String, // ID of the user who wrote the comment
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)