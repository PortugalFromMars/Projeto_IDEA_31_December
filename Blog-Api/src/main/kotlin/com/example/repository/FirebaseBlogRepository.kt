package com.example.repository

import com.example.models.BlogPost
import com.example.models.Comment
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.tasks.await

class FirebaseBlogRepository : BlogRepository {

   
    private val db: Firestore = FirestoreClient.getFirestore()
    // Blog Post Operations

    override suspend fun getAllBlogPosts(): List<BlogPost> {
        val querySnapshot = db.collection("blogPosts").get().await()
        return querySnapshot.documents.mapNotNull { it.toObject(BlogPost::class.java) }
    }

    override suspend fun getBlogPostById(id: String): BlogPost? {
        val documentSnapshot = db.collection("blogPosts").document(id).get().await()
        return if (documentSnapshot.exists()) {
            documentSnapshot.toObject(BlogPost::class.java)
        } else {
            null
        }
    }

    override suspend fun createBlogPost(post: BlogPost): String {
        val docRef = db.collection("blogPosts").add(post).await()
        return docRef.id
    }

    override suspend fun updateBlogPost(id: String, post: BlogPost): Boolean {
        db.collection("blogPosts").document(id).set(post).await()
        return true
    }

    override suspend fun deleteBlogPost(id: String): Boolean {
        db.collection("blogPosts").document(id).delete().await()
        return true
    }

    // Comment Operations

    override suspend fun getCommentsForPost(postId: String): List<Comment> {
        val querySnapshot = db.collection("comments")
            .whereEqualTo("postId", postId)
            .get()
            .await()
        return querySnapshot.documents.mapNotNull { it.toObject(Comment::class.java) }
    }

    override suspend fun createComment(comment: Comment): String {
        val docRef = db.collection("comments").add(comment).await()
        return docRef.id
    }

    override suspend fun updateComment(id: String, comment: Comment): Boolean {
        db.collection("comments").document(id).set(comment).await()
        return true
    }

    override suspend fun deleteComment(id: String): Boolean {
        db.collection("comments").document(id).delete().await()
        return true
    }
}