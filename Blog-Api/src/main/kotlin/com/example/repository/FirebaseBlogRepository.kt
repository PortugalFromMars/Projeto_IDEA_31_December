package com.example.repository

import com.example.models.BlogPost
import com.example.models.Comment
import com.google.firebase.firestore.FirebaseFirestore  // Import FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot // Import DocumentSnapshot
import com.google.firebase.firestore.FieldValue         // Import FieldValue
import kotlinx.coroutines.tasks.await

object FirestoreCollections {
    const val BLOG_POSTS = "blogPosts"
    const val COMMENTS = "comments"
}

class FirebaseBlogRepository : BlogRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getAllBlogPosts(): List<BlogPost> {
        return try {
            val querySnapshot = db.collection(FirestoreCollections.BLOG_POSTS).get().await()
            querySnapshot.documents.mapNotNull { document: DocumentSnapshot -> // Type annotation
                document.toObject(BlogPost::class.java)
            }
        } catch (e: Exception) {
            throw GetPostsException("Failed to retrieve blog posts", e)
        }
    }


    override suspend fun getBlogPostById(id: String): BlogPost? {
        return try {
            val documentSnapshot = db.collection(FirestoreCollections.BLOG_POSTS).document(id).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(BlogPost::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            throw GetPostByIdException("Failed to retrieve blog post by ID", e)
        }
    }

    override suspend fun createBlogPost(post: BlogPost): String {
        return try {
            val docRef = db.collection(FirestoreCollections.BLOG_POSTS).add(
                hashMapOf(
                    "title" to post.title,
                    "content" to post.content,
                    "authorId" to post.authorId,
                    "likes" to post.likes,
                    "timestamp" to FieldValue.serverTimestamp() // Use server timestamp
                )
            ).await()
            docRef.id
        } catch (e: Exception) {
            throw CreatePostException("Failed to create blog post", e)
        }
    }

    override suspend fun updateBlogPost(id: String, post: BlogPost): Boolean {
        return try {
            db.collection(FirestoreCollections.BLOG_POSTS).document(id).set(post).await()
            true
        } catch (e: Exception) {
            throw UpdatePostException("Failed to update blog post", e)
        }
    }

    override suspend fun deleteBlogPost(id: String): Boolean {
        return try {
            db.collection(FirestoreCollections.BLOG_POSTS).document(id).delete().await()
            true
        } catch (e: Exception) {
            throw DeletePostException("Failed to delete blog post", e)
        }
    }

    override suspend fun getCommentsForPost(postId: String): List<Comment> {
        return try {
            val querySnapshot = db.collection(FirestoreCollections.COMMENTS)
                .whereEqualTo("postId", postId)
                .get().await()
            querySnapshot.documents.mapNotNull { document: DocumentSnapshot -> // Type annotation
                document.toObject(Comment::class.java)
            }
        } catch (e: Exception) {
            throw GetCommentsException("Failed to retrieve comments for the blog post", e)
        }
    }

    override suspend fun createComment(comment: Comment): String {
        return try {
            val docRef = db.collection(FirestoreCollections.COMMENTS).add(comment).await()
            docRef.id
        } catch (e: Exception) {
            throw CreateCommentException("Failed to create comment", e)
        }
    }

    override suspend fun updateComment(id: String, comment: Comment): Boolean {
        return try {
            db.collection(FirestoreCollections.COMMENTS).document(id).set(comment).await()
            true
        } catch (e: Exception) {
            throw UpdateCommentException("Failed to update comment", e)
        }
    }

    override suspend fun deleteComment(id: String): Boolean {
        return try {
            db.collection(FirestoreCollections.COMMENTS).document(id).delete().await()
            true
        } catch (e: Exception) {
            throw DeleteCommentException("Failed to delete comment", e)
        }
    }
}