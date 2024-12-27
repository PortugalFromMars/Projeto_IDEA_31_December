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

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                val updatedPost = call.receive<BlogPost>()
                val success = blogRepository.updateBlogPost(id, updatedPost)
                if (success) {
                    call.respond(HttpStatusCode.OK, "Post updated successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Post not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error updating post: ${e.message}")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing ID")
            try {
                val success = blogRepository.deleteBlogPost(id)
                if (success) {
                    call.respond(HttpStatusCode.OK, "Post deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Post not found")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error deleting post: ${e.message}")
            }
        }
    }
}