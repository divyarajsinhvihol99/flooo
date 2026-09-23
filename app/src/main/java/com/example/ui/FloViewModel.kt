package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.FloDatabase
import com.example.data.model.ChatMessage
import com.example.data.model.CollabGig
import com.example.data.model.CollabRequest
import com.example.data.model.CreativePost
import com.example.data.model.CreativeUser
import com.example.data.repository.FloRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    SPLASH,
    WELCOME_1,
    WELCOME_2,
    LOGIN,
    REGISTER,
    HOME,
    EXPLORE,
    SEARCH,
    CREATE_EVENT_STORY,
    TASK_SCHEDULE,
    GALLERY,
    UPLOAD_SELECTION,
    PROJECT_DETAILS,
    CHAT_LIST,
    CHAT_BOX,
    PROFILE,
    NOTIFICATIONS,
    MOODBOARD_COLLAB
}

class FloViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FloRepository

    val posts: StateFlow<List<CreativePost>>
    val users: StateFlow<List<CreativeUser>>
    val requests: StateFlow<List<CollabRequest>>
    val gigs: StateFlow<List<CollabGig>>

    private val _currentScreen = MutableStateFlow(Screen.SPLASH)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _activeChatId = MutableStateFlow("@ridzjcob")
    val activeChatId: StateFlow<String> = _activeChatId.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _selectedUserProfile = MutableStateFlow<CreativeUser?>(null)
    val selectedUserProfile: StateFlow<CreativeUser?> = _selectedUserProfile.asStateFlow()

    private val _selectedProjectIndex = MutableStateFlow(0)
    val selectedProjectIndex: StateFlow<Int> = _selectedProjectIndex.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchFilter = MutableStateFlow("Nearby") // Place, People, Nearby, Post, Gear
    val searchFilter: StateFlow<String> = _searchFilter.asStateFlow()

    // Upload draft state
    private val _draftTitle = MutableStateFlow("Expositions")
    val draftTitle: StateFlow<String> = _draftTitle.asStateFlow()

    private val _draftCaption = MutableStateFlow("Discomfort creates new ways of creativity.")
    val draftCaption: StateFlow<String> = _draftCaption.asStateFlow()

    private val _draftPlace = MutableStateFlow("Istanbul street")
    val draftPlace: StateFlow<String> = _draftPlace.asStateFlow()

    private val _draftGear = MutableStateFlow("Sony A7III • 50mm f/1.8")
    val draftGear: StateFlow<String> = _draftGear.asStateFlow()

    private val _draftCategory = MutableStateFlow("Street")
    val draftCategory: StateFlow<String> = _draftCategory.asStateFlow()

    private val _selectedPhotosForUpload = MutableStateFlow<Set<Int>>(setOf(0, 2, 4))
    val selectedPhotosForUpload: StateFlow<Set<Int>> = _selectedPhotosForUpload.asStateFlow()

    private val _notificationFeedback = MutableStateFlow<String?>(null)
    val notificationFeedback: StateFlow<String?> = _notificationFeedback.asStateFlow()

    init {
        val db = FloDatabase.getInstance(application)
        repository = FloRepository(db.floDao())

        posts = repository.allPosts.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        users = repository.allUsers.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        requests = repository.allRequests.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        gigs = repository.allGigs.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }

        // Collect chat messages whenever activeChatId changes
        viewModelScope.launch {
            _activeChatId.collect { chatId ->
                repository.getChatMessages(chatId).collect { msgs ->
                    _chatMessages.value = msgs
                }
            }
        }
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun openChatWith(userHandle: String) {
        _activeChatId.value = userHandle
        _currentScreen.value = Screen.CHAT_BOX
    }

    fun openProfile(user: CreativeUser?) {
        _selectedUserProfile.value = user
        _currentScreen.value = Screen.PROFILE
    }

    fun selectProjectIndex(index: Int) {
        _selectedProjectIndex.value = index
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSearchFilter(filter: String) {
        _searchFilter.value = filter
    }

    fun togglePhotoSelection(photoIndex: Int) {
        val current = _selectedPhotosForUpload.value.toMutableSet()
        if (current.contains(photoIndex)) {
            current.remove(photoIndex)
        } else {
            current.add(photoIndex)
        }
        _selectedPhotosForUpload.value = current
    }

    fun updateDraftTitle(title: String) { _draftTitle.value = title }
    fun updateDraftCaption(caption: String) { _draftCaption.value = caption }
    fun updateDraftPlace(place: String) { _draftPlace.value = place }
    fun updateDraftGear(gear: String) { _draftGear.value = gear }
    fun updateDraftCategory(category: String) { _draftCategory.value = category }

    fun togglePostLike(post: CreativePost) {
        viewModelScope.launch {
            repository.toggleLike(post)
        }
    }

    fun togglePostSave(post: CreativePost) {
        viewModelScope.launch {
            repository.toggleSave(post)
        }
    }

    fun toggleUserFollow(user: CreativeUser) {
        viewModelScope.launch {
            repository.toggleFollow(user.handle, user.isFollowing)
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        val currentChat = _activeChatId.value
        viewModelScope.launch {
            repository.sendMessage(
                chatId = currentChat,
                senderHandle = "@me",
                senderName = "You",
                text = text.trim(),
                isFromMe = true
            )
            // Mock immediate collaborative response from the peer to showcase real-time feel
            kotlinx.coroutines.delay(1200)
            val peerName = if (currentChat == "@ridzjcob") "Ridhwan Nordin" else "Collaborator"
            val responses = listOf(
                "Great eye! Let's definitely align on the composition for the next shoot.",
                "Agreed! I'll prep the RAW files and color LUTs for you.",
                "Sounds solid. Let's lock in the schedule for golden hour.",
                "I'll pack the 35mm and 85mm primes for that exact look!"
            )
            val randomReply = responses.random()
            repository.sendMessage(
                chatId = currentChat,
                senderHandle = currentChat,
                senderName = peerName,
                text = randomReply,
                isFromMe = false
            )
        }
    }

    fun acceptRequest(reqId: Long) {
        viewModelScope.launch {
            repository.updateRequestStatus(reqId, "ACCEPTED")
            _notificationFeedback.value = "Collaboration request accepted!"
        }
    }

    fun declineRequest(reqId: Long) {
        viewModelScope.launch {
            repository.updateRequestStatus(reqId, "DECLINED")
            _notificationFeedback.value = "Request declined"
        }
    }

    fun applyToGig(gig: CollabGig) {
        viewModelScope.launch {
            repository.applyToGig(gig)
            _notificationFeedback.value = "Application submitted with your Flo portfolio!"
        }
    }

    fun publishDraftProject() {
        viewModelScope.launch {
            val newPost = CreativePost(
                creatorId = "me",
                creatorName = "Ekeya Ninoka",
                creatorHandle = "@ninokaek",
                creatorAvatar = "avatar_ekeya",
                creatorRole = "Street & Documentary",
                title = _draftTitle.value.ifBlank { "Untitled Series" },
                caption = _draftCaption.value,
                location = _draftPlace.value.ifBlank { "Unspecified Location" },
                cameraGear = _draftGear.value.ifBlank { "Sony A7IV" },
                category = _draftCategory.value,
                imageUrls = "post_fire_sparks",
                likesCount = 1,
                isLiked = true,
                isSaved = false,
                commentsCount = 0,
                timestamp = System.currentTimeMillis()
            )
            repository.insertPost(newPost)
            _notificationFeedback.value = "Project published to your Flo portfolio!"
            _currentScreen.value = Screen.HOME
        }
    }

    fun clearNotificationFeedback() {
        _notificationFeedback.value = null
    }
}
