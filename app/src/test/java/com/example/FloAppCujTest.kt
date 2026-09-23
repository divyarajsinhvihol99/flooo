package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.FloDatabase
import com.example.data.repository.FloRepository
import com.example.ui.FloViewModel
import com.example.ui.Screen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FloAppCujTest {

    private lateinit var app: Application
    private lateinit var viewModel: FloViewModel
    private lateinit var repository: FloRepository

    @Before
    fun setup() {
        app = ApplicationProvider.getApplicationContext()
        val db = FloDatabase.getInstance(app)
        repository = FloRepository(db.floDao())
        viewModel = FloViewModel(app)
    }

    @Test
    fun testInitialSeedDataAndPosts() = runBlocking {
        repository.seedInitialDataIfEmpty()
        val posts = repository.allPosts.first()
        assertTrue("Expected seeded posts", posts.isNotEmpty())

        val users = repository.allUsers.first()
        assertTrue("Expected seeded creators", users.isNotEmpty())

        val ridhwan = users.find { it.handle == "@ridzjcob" }
        assertNotNull("Ridhwan Nordin should exist", ridhwan)
        assertEquals("Tokyo & Berlin", ridhwan?.location)
    }

    @Test
    fun testTogglePostLike() = runBlocking {
        repository.seedInitialDataIfEmpty()
        val initialPosts = repository.allPosts.first()
        val firstPost = initialPosts.first()

        val initialLikes = firstPost.likesCount
        val initialLiked = firstPost.isLiked

        repository.toggleLike(firstPost)

        val updatedPosts = repository.allPosts.first()
        val updatedPost = updatedPosts.first { it.id == firstPost.id }

        assertEquals(!initialLiked, updatedPost.isLiked)
        if (!initialLiked) {
            assertEquals(initialLikes + 1, updatedPost.likesCount)
        }
    }

    @Test
    fun testSendMessage() = runBlocking {
        repository.seedInitialDataIfEmpty()
        val chatId = "@ssialk"
        repository.sendMessage(
            chatId = chatId,
            senderHandle = "@me",
            senderName = "You",
            text = "Excited to collaborate on the Paris lookbook!",
            isFromMe = true
        )

        val messages = repository.getChatMessages(chatId).first()
        assertTrue(messages.any { it.text.contains("Paris lookbook") })
    }

    @Test
    fun testApplyToGig() = runBlocking {
        repository.seedInitialDataIfEmpty()
        val gigs = repository.allGigs.first()
        val targetGig = gigs.first { !it.isApplied }

        repository.applyToGig(targetGig)

        val updatedGigs = repository.allGigs.first()
        val updatedGig = updatedGigs.first { it.id == targetGig.id }

        assertTrue(updatedGig.isApplied)
        assertEquals(targetGig.applicantsCount + 1, updatedGig.applicantsCount)
    }

    @Test
    fun testNavigationStateFlow() {
        assertEquals(Screen.SPLASH, viewModel.currentScreen.value)
        viewModel.navigateTo(Screen.HOME)
        assertEquals(Screen.HOME, viewModel.currentScreen.value)
        viewModel.navigateTo(Screen.EXPLORE)
        assertEquals(Screen.EXPLORE, viewModel.currentScreen.value)
    }
}
