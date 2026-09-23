package com.example.data.repository

import com.example.data.local.FloDao
import com.example.data.model.ChatMessage
import com.example.data.model.CollabGig
import com.example.data.model.CollabRequest
import com.example.data.model.CreativePost
import com.example.data.model.CreativeUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FloRepository(private val dao: FloDao) {

    val allPosts: Flow<List<CreativePost>> = dao.getAllPosts()
    val allUsers: Flow<List<CreativeUser>> = dao.getAllUsers()
    val allRequests: Flow<List<CollabRequest>> = dao.getAllRequests()
    val allGigs: Flow<List<CollabGig>> = dao.getAllGigs()

    fun getChatMessages(chatId: String): Flow<List<ChatMessage>> = dao.getChatMessages(chatId)
    fun getUser(handle: String): Flow<CreativeUser?> = dao.getUserByHandle(handle)
    fun getPostsByHandle(handle: String): Flow<List<CreativePost>> = dao.getPostsByHandle(handle)

    suspend fun insertPost(post: CreativePost): Long = dao.insertPost(post)

    suspend fun toggleLike(post: CreativePost) {
        val newLiked = !post.isLiked
        val newCount = if (newLiked) post.likesCount + 1 else maxOf(0, post.likesCount - 1)
        dao.updatePostLike(post.id, newLiked, newCount)
    }

    suspend fun toggleSave(post: CreativePost) {
        dao.updatePostSaved(post.id, !post.isSaved)
    }

    suspend fun toggleFollow(handle: String, currentFollowing: Boolean) {
        dao.updateFollowState(handle, !currentFollowing)
    }

    suspend fun sendMessage(chatId: String, senderHandle: String, senderName: String, text: String, imageUrl: String = "", isFromMe: Boolean = true) {
        val msg = ChatMessage(
            chatId = chatId,
            senderHandle = senderHandle,
            senderName = senderName,
            text = text,
            imageUrl = imageUrl,
            isFromMe = isFromMe,
            timestamp = System.currentTimeMillis()
        )
        dao.insertChatMessage(msg)
    }

    suspend fun updateRequestStatus(id: Long, status: String) {
        dao.updateRequestStatus(id, status)
    }

    suspend fun applyToGig(gig: CollabGig) {
        if (!gig.isApplied) {
            dao.updateGigApplied(gig.id, isApplied = true, applicantsCount = gig.applicantsCount + 1)
        }
    }

    suspend fun seedInitialDataIfEmpty() {
        val existingPosts = dao.getAllPosts().first()
        if (existingPosts.isEmpty()) {
            val defaultUsers = listOf(
                CreativeUser(
                    handle = "@ninokaek",
                    name = "Ekeya Ninoka",
                    role = "Street Photographer",
                    location = "Istanbul, Turkey",
                    bio = "Exploring the busy markets and capturing unseen emotions. Street photography is passion and patience supported by observation.",
                    avatarUrl = "avatar_ekeya",
                    isFollowing = false,
                    isAvailableForHire = true,
                    projectsCount = 18,
                    followersCount = 3840,
                    followingCount = 420,
                    cameraKit = "Sony A7RV • Leica M11 • 35mm f/1.4 Summilux"
                ),
                CreativeUser(
                    handle = "@ridzjcob",
                    name = "Ridhwan Nordin",
                    role = "Architectural & Urban Photographer",
                    location = "Tokyo & Berlin",
                    bio = "Documenting lines, shadows, and architectural brutalism across global metropolises.",
                    avatarUrl = "avatar_ridhwan",
                    isFollowing = true,
                    isAvailableForHire = true,
                    projectsCount = 14,
                    followersCount = 5210,
                    followingCount = 290,
                    cameraKit = "Sony A7III • 24-70mm GM • 50mm f/1.8"
                ),
                CreativeUser(
                    handle = "@kimikach",
                    name = "Chizhua Kimiko",
                    role = "Cinematographer & Colorist",
                    location = "Kyoto, Japan",
                    bio = "Visual storyteller, anamorphic enthusiast, grading in DaVinci Resolve.",
                    avatarUrl = "avatar_chizhua",
                    isFollowing = false,
                    isAvailableForHire = true,
                    projectsCount = 22,
                    followersCount = 9840,
                    followingCount = 510,
                    cameraKit = "RED Komodo 6K • Atlas Orion Anamorphic"
                ),
                CreativeUser(
                    handle = "@chengalino",
                    name = "Alino Cheng",
                    role = "Fine Art & Landscape Photographer",
                    location = "Vancouver, BC",
                    bio = "Photo is not just a visual, Its a frozen time frame and emotion.",
                    avatarUrl = "avatar_alino",
                    isFollowing = false,
                    isAvailableForHire = true,
                    projectsCount = 9,
                    followersCount = 2190,
                    followingCount = 180,
                    cameraKit = "Fujifilm GFX 100 II • GF 32-64mm"
                ),
                CreativeUser(
                    handle = "@ssialk",
                    name = "Sonia Clerk",
                    role = "Fashion & Editorial Director",
                    location = "Paris & Milan",
                    bio = "Vogue & Harper's Bazaar contributor. Styling, lighting, analog 120mm.",
                    avatarUrl = "avatar_sonia",
                    isFollowing = false,
                    isAvailableForHire = true,
                    projectsCount = 31,
                    followersCount = 14200,
                    followingCount = 610,
                    cameraKit = "Hasselblad 503CW • Kodak Portra 400"
                ),
                CreativeUser(
                    handle = "@jaasmehra",
                    name = "Jasleen Mehra",
                    role = "Commercial Video Producer",
                    location = "London, UK",
                    bio = "Directing commercial campaigns, brand documentaries and dynamic visuals.",
                    avatarUrl = "avatar_jasleen",
                    isFollowing = true,
                    isAvailableForHire = true,
                    projectsCount = 26,
                    followersCount = 7600,
                    followingCount = 390,
                    cameraKit = "ARRI Alexa Mini LF • Cooke S4/i"
                )
            )
            dao.insertUsers(defaultUsers)

            val defaultPosts = listOf(
                CreativePost(
                    creatorId = "1",
                    creatorName = "Ridhwan Nordin",
                    creatorHandle = "@ridzjcob",
                    creatorAvatar = "avatar_ridhwan",
                    creatorRole = "Urban Photographer",
                    title = "Geometric Ascension",
                    caption = "frame into frame, crossing boundaries and leading lines.",
                    location = "Tokyo Metro, Ginza",
                    cameraGear = "Sony A7III • 24mm f/1.4 GM • 1/250s ISO 160",
                    category = "Architecture",
                    imageUrls = "post_tokyo_stairs",
                    likesCount = 342,
                    isLiked = false,
                    isSaved = true,
                    commentsCount = 28,
                    timestamp = System.currentTimeMillis() - 3600000 * 2
                ),
                CreativePost(
                    creatorId = "2",
                    creatorName = "Ridhwan Nordin",
                    creatorHandle = "@ridzjcob",
                    creatorAvatar = "avatar_ridhwan",
                    creatorRole = "Street Photographer",
                    title = "Spark Foundry",
                    caption = "Long exposure spark choreography in darkness. The energy of raw iron and fire.",
                    location = "Old Quarter, Hanoi",
                    cameraGear = "Sony A7III • 50mm f/1.8 • 4s ISO 50",
                    category = "Night & Long Exposure",
                    imageUrls = "post_fire_sparks",
                    likesCount = 512,
                    isLiked = true,
                    isSaved = false,
                    commentsCount = 47,
                    timestamp = System.currentTimeMillis() - 3600000 * 6
                ),
                CreativePost(
                    creatorId = "3",
                    creatorName = "Ekeya Ninoka",
                    creatorHandle = "@ninokaek",
                    creatorAvatar = "avatar_ekeya",
                    creatorRole = "Documentary & Street",
                    title = "Istanbul Bazaar Life",
                    caption = "Exploring the busy markets and capturing unseen emotions. Street photography is passion and patience supported by observation.",
                    location = "Grand Bazaar, Istanbul",
                    cameraGear = "Leica M11 • 35mm Summilux f/1.4 • 1/500s ISO 400",
                    category = "Street",
                    imageUrls = "post_bazaar_life",
                    likesCount = 890,
                    isLiked = true,
                    isSaved = true,
                    commentsCount = 64,
                    timestamp = System.currentTimeMillis() - 3600000 * 12
                ),
                CreativePost(
                    creatorId = "4",
                    creatorName = "Alino Cheng",
                    creatorHandle = "@chengalino",
                    creatorAvatar = "avatar_alino",
                    creatorRole = "Landscape Fine Art",
                    title = "Solitude Ridge",
                    caption = "Photo is not just a visual, Its a frozen time frame and emotion.",
                    location = "Pacific Northwest, BC",
                    cameraGear = "Fujifilm GFX 100 II • GF 45mm • 1/125s ISO 100",
                    category = "Landscape",
                    imageUrls = "post_misty_woods",
                    likesCount = 638,
                    isLiked = false,
                    isSaved = false,
                    commentsCount = 33,
                    timestamp = System.currentTimeMillis() - 3600000 * 24
                )
            )
            dao.insertPosts(defaultPosts)

            val defaultRequests = listOf(
                CollabRequest(
                    senderName = "Ridhwan Nordin",
                    senderHandle = "@ridzjcob",
                    senderAvatar = "avatar_ridhwan",
                    type = "REQUEST",
                    projectTitle = "Tokyo Brutalism Collab",
                    message = "Would love to co-shoot an architectural series in Shibuya next month!",
                    status = "PENDING",
                    timestamp = System.currentTimeMillis() - 1800000
                ),
                CollabRequest(
                    senderName = "Sonia Clerk",
                    senderHandle = "@ssialk",
                    senderAvatar = "avatar_sonia",
                    type = "REQUEST",
                    projectTitle = "Paris Fashion Week BTS",
                    message = "Looking for a documentary second shooter for runway backstage.",
                    status = "PENDING",
                    timestamp = System.currentTimeMillis() - 7200000
                ),
                CollabRequest(
                    senderName = "Chizhua Kimiko",
                    senderHandle = "@kimikach",
                    senderAvatar = "avatar_chizhua",
                    type = "REQUEST",
                    projectTitle = "Anamorphic Video Short",
                    message = "Need creative feedback on color grade LUTs for our upcoming short film.",
                    status = "ACCEPTED",
                    timestamp = System.currentTimeMillis() - 14400000
                ),
                CollabRequest(
                    senderName = "Jasleen Mehra",
                    senderHandle = "@jaasmehra",
                    senderAvatar = "avatar_jasleen",
                    type = "INQUIRY",
                    projectTitle = "Commercial Campaign Director",
                    message = "Directing a 3-day commercial shoot for global sportswear brand in Istanbul. Day rate $1,200.",
                    status = "PENDING",
                    timestamp = System.currentTimeMillis() - 3600000
                ),
                CollabRequest(
                    senderName = "Alino Cheng",
                    senderHandle = "@chengalino",
                    senderAvatar = "avatar_alino",
                    type = "INQUIRY",
                    projectTitle = "Gallery Print Exhibition",
                    message = "Inviting 4 curated photographs from your Istanbul series for Vancouver gallery print show.",
                    status = "PENDING",
                    timestamp = System.currentTimeMillis() - 28800000
                )
            )
            dao.insertRequests(defaultRequests)

            val defaultGigs = listOf(
                CollabGig(
                    title = "I need one talented Candid shooter, Apply here",
                    posterName = "Chizhua Kimiko",
                    posterHandle = "@kimikach",
                    posterAvatar = "avatar_chizhua",
                    location = "Kyoto, Japan • Gion District",
                    category = "Candid & Documentary",
                    compensation = "$650/day + Travel",
                    description = "Shooting natural, candid cultural interactions during dusk festival in traditional streets.",
                    isApplied = false,
                    applicantsCount = 7,
                    timestamp = System.currentTimeMillis() - 3600000 * 3
                ),
                CollabGig(
                    title = "Drone Operator & FPV Pilot for Coastal Commercial",
                    posterName = "Jasleen Mehra",
                    posterHandle = "@jaasmehra",
                    posterAvatar = "avatar_jasleen",
                    location = "Amalfi Coast, Italy",
                    category = "Drone Cinematography",
                    compensation = "$1,100/day",
                    description = "Need FAA/EASA certified drone pilot with Inspire 3 or high-speed FPV rig for yacht chase sequence.",
                    isApplied = false,
                    applicantsCount = 12,
                    timestamp = System.currentTimeMillis() - 3600000 * 5
                ),
                CollabGig(
                    title = "Second Shooter: Moody Autumn Wedding & Portraiture",
                    posterName = "Sonia Clerk",
                    posterHandle = "@ssialk",
                    posterAvatar = "avatar_sonia",
                    location = "Provence, France",
                    category = "Editorial & Portrait",
                    compensation = "$800 flat rate",
                    description = "Looking for an editorial eye with prime lenses (f1.2 / f1.4) to capture candid guest moments.",
                    isApplied = true,
                    applicantsCount = 5,
                    timestamp = System.currentTimeMillis() - 3600000 * 8
                )
            )
            dao.insertGigs(defaultGigs)

            // Seed initial chat with Ridhwan Nordin (@ridzjcob) matching the reference screenshot!
            val chatWithRidhwan = listOf(
                ChatMessage(
                    chatId = "@ridzjcob",
                    senderHandle = "@ridzjcob",
                    senderName = "Ridhwan Nordin",
                    text = "Really love your most recent photo. I've been trying to capture the same thing for a few months and would love some tips!",
                    isFromMe = false,
                    timestamp = System.currentTimeMillis() - 1200000
                ),
                ChatMessage(
                    chatId = "@ridzjcob",
                    senderHandle = "@me",
                    senderName = "You",
                    text = "A fast 50mm like f1.8 would help with the bokeh.",
                    isFromMe = true,
                    timestamp = System.currentTimeMillis() - 600000
                ),
                ChatMessage(
                    chatId = "@ridzjcob",
                    senderHandle = "@me",
                    senderName = "You",
                    text = "What do you shoot btw?",
                    isFromMe = true,
                    timestamp = System.currentTimeMillis() - 300000
                ),
                ChatMessage(
                    chatId = "@ridzjcob",
                    senderHandle = "@ridzjcob",
                    senderName = "Ridhwan Nordin",
                    text = "Mostly street & architecture! Using the Sony A7III with a 24-70 GM right now.",
                    isFromMe = false,
                    timestamp = System.currentTimeMillis() - 60000
                )
            )
            dao.insertChatMessages(chatWithRidhwan)
        }
    }
}
