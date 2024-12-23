package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,             // User ID from Firebase Authentication
    val email: String,
    val displayName: String? = null, // Optional display name
    // ... other fields you might want to store
)