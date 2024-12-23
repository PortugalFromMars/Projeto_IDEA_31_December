package com.example

import com.example.repository.FirebaseBlogRepository
import com.example.routes.blogRoutes
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.FileInputStream

fun main() {
    initFirebase() // Initialize Firebase

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting() // Call your routing configuration
}

fun Application.configureRouting() {
    val blogRepository = FirebaseBlogRepository() // Create repository instance

    routing {
        get("/") {
            call.respondText("Hello, Ktor Blog!")
        }
        blogRoutes(blogRepository) // Set up blog routes
    }
}

private fun initFirebase() {
    val serviceAccount = Application::class.java.classLoader.getResourceAsStream("blog-api-274a8-firebase-adminsdk-lkck3-25c1479991.json")
        ?: throw RuntimeException("Firebase Admin SDK file not found")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("your-firebase-database-url") // Replace with your database URL
        .build()

    FirebaseApp.initializeApp(options)
}