package com.example.routes

import com.example.models.BlogPost
import com.example.repository.BlogRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.blogRoutes(blogRepository: BlogRepository) {
    route("/blog") {
        get {
            try {
                val posts = blogRepository.getAllBlogPosts()
                call.respond(posts)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error getting posts: ${e.message}")
            }
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                val post = blogRepository.getBlogPostById(id)
                if (post != null) {
                    call.respond(post)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Post not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error getting post: ${e.message}")
            }
        }

        post {
            try {
                val post = call.receive<BlogPost>()
                val newPostId = blogRepository.createBlogPost(post)
                call.respond(HttpStatusCode.Created, blogRepository.getBlogPostById(newPostId)!!)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error creating post: ${e.message}")
            }
        }

        // ... other routes (PUT update, DELETE) for blog posts and comments
    }
}