// Application.kt
package com.example

import com.example.repository.BlogRepository
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
    initFirebase()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    // Manual Dependency Injection (for now) - Consider using Koin or Kodein later
    val blogRepository: BlogRepository = FirebaseBlogRepository()

    configureRouting(blogRepository)
}

fun Application.configureRouting(blogRepository: BlogRepository) {
    routing {
        get("/") {
            call.respondText("Hello, Ktor Blog!")
        }
        blogRoutes(blogRepository)
    }
}

private fun initFirebase() {
    val serviceAccount = Application::class.java.classLoader.getResourceAsStream("blog-api-274a8-firebase-adminsdk-lkck3-25c1479991.json")
        ?: throw RuntimeException("Firebase Admin SDK file not found")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        // .setDatabaseUrl("your-firebase-database-url") // Remove or replace if not using Realtime Database
        .build()

    FirebaseApp.initializeApp(options)
}