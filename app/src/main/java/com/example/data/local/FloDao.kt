package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ChatMessage
import com.example.data.model.CollabGig
import com.example.data.model.CollabRequest
import com.example.data.model.CreativePost
import com.example.data.model.CreativeUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FloDao {

    // Posts
    @Query("SELECT * FROM creative_posts ORDER BY timestamp DESC")
    fun getAllPosts(): Flow<List<CreativePost>>

    @Query("SELECT * FROM creative_posts WHERE creatorHandle = :handle ORDER BY timestamp DESC")
    fun getPostsByHandle(handle: String): Flow<List<CreativePost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CreativePost): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<CreativePost>)

    @Query("UPDATE creative_posts SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :id")
    suspend fun updatePostLike(id: Long, isLiked: Boolean, likesCount: Int)

    @Query("UPDATE creative_posts SET isSaved = :isSaved WHERE id = :id")
    suspend fun updatePostSaved(id: Long, isSaved: Boolean)

    // Users
    @Query("SELECT * FROM creative_users")
    fun getAllUsers(): Flow<List<CreativeUser>>

    @Query("SELECT * FROM creative_users WHERE handle = :handle LIMIT 1")
    fun getUserByHandle(handle: String): Flow<CreativeUser?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<CreativeUser>)

    @Query("UPDATE creative_users SET isFollowing = :isFollowing WHERE handle = :handle")
    suspend fun updateFollowState(handle: String, isFollowing: Boolean)

    // Chat
    @Query("SELECT * FROM chat_messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getChatMessages(chatId: String): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessages(messages: List<ChatMessage>)

    // Collab Requests & Inquiries
    @Query("SELECT * FROM collab_requests ORDER BY timestamp DESC")
    fun getAllRequests(): Flow<List<CollabRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequests(requests: List<CollabRequest>)

    @Query("UPDATE collab_requests SET status = :status WHERE id = :id")
    suspend fun updateRequestStatus(id: Long, status: String)

    // Gigs
    @Query("SELECT * FROM collab_gigs ORDER BY timestamp DESC")
    fun getAllGigs(): Flow<List<CollabGig>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGigs(gigs: List<CollabGig>)

    @Query("UPDATE collab_gigs SET isApplied = :isApplied, applicantsCount = :applicantsCount WHERE id = :id")
    suspend fun updateGigApplied(id: Long, isApplied: Boolean, applicantsCount: Int)
}
