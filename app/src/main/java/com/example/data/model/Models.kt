package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creative_posts")
data class CreativePost(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val creatorId: String,
    val creatorName: String,
    val creatorHandle: String,
    val creatorAvatar: String,
    val creatorRole: String = "Photographer",
    val title: String,
    val caption: String,
    val location: String,
    val cameraGear: String,
    val category: String,
    val imageUrls: String, // Comma-separated image resource or URLs
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val commentsCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "creative_users")
data class CreativeUser(
    @PrimaryKey
    val handle: String,
    val name: String,
    val role: String,
    val location: String,
    val bio: String,
    val avatarUrl: String,
    val isFollowing: Boolean = false,
    val isAvailableForHire: Boolean = true,
    val projectsCount: Int = 12,
    val followersCount: Int = 1420,
    val followingCount: Int = 380,
    val cameraKit: String = "Sony A7IV • 35mm f/1.4 GM • 85mm f/1.8"
)

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chatId: String,
    val senderHandle: String,
    val senderName: String,
    val text: String,
    val imageUrl: String = "",
    val isFromMe: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "collab_requests")
data class CollabRequest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderName: String,
    val senderHandle: String,
    val senderAvatar: String,
    val type: String, // "REQUEST" or "INQUIRY"
    val projectTitle: String,
    val message: String,
    val status: String = "PENDING", // PENDING, ACCEPTED, DECLINED
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "collab_gigs")
data class CollabGig(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val posterName: String,
    val posterHandle: String,
    val posterAvatar: String,
    val location: String,
    val category: String, // "Candid shooter", "Drone", "Commercial", "Colorist"
    val compensation: String, // e.g. "$650/day", "Paid Collab", "Revenue Share"
    val description: String,
    val isApplied: Boolean = false,
    val applicantsCount: Int = 4,
    val timestamp: Long = System.currentTimeMillis()
)
