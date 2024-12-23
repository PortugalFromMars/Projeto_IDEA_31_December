package com.example.repository

import com.example.models.BlogPost
import com.example.models.Comment

// Define custom exceptions (e.g., in Exceptions.kt)
class GetPostsException(message: String, cause: Throwable? = null) : Exception(message, cause)
class GetPostByIdException(message: String, cause: Throwable? = null) : Exception(message, cause)
class CreatePostException(message: String, cause: Throwable? = null) : Exception(message, cause)
class UpdatePostException(message: String, cause: Throwable? = null) : Exception(message, cause)
class DeletePostException(message: String, cause: Throwable? = null) : Exception(message, cause)
class GetCommentsException(message: String, cause: Throwable? = null) : Exception(message, cause)
class CreateCommentException(message: String, cause: Throwable? = null) : Exception(message, cause)
class UpdateCommentException(message: String, cause: Throwable? = null) : Exception(message, cause)
class DeleteCommentException(message: String, cause: Throwable? = null) : Exception(message, cause)

interface BlogRepository {
    @Throws(GetPostsException::class)
    suspend fun getAllBlogPosts(): List<BlogPost>

    @Throws(GetPostByIdException::class)
    suspend fun getBlogPostById(id: String): BlogPost?

    @Throws(CreatePostException::class)
    suspend fun createBlogPost(post: BlogPost): String

    @Throws(UpdatePostException::class)
    suspend fun updateBlogPost(id: String, post: BlogPost): Boolean

    @Throws(DeletePostException::class)
    suspend fun deleteBlogPost(id: String): Boolean

    @Throws(GetCommentsException::class)
    suspend fun getCommentsForPost(postId: String): List<Comment>

    @Throws(CreateCommentException::class)
    suspend fun createComment(comment: Comment): String

    @Throws(UpdateCommentException::class)
    suspend fun updateComment(id: String, comment: Comment): Boolean

    @Throws(DeleteCommentException::class)
    suspend fun deleteComment(id: String): Boolean
}