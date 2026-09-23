package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.FloViewModel
import com.example.ui.Screen
import com.example.ui.components.FloWordmark
import com.example.ui.screens.ChatBoxScreen
import com.example.ui.screens.ChatListScreen
import com.example.ui.screens.CreateEventStoryScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MoodboardCollabScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProjectDetailsScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TaskScheduleScreen
import com.example.ui.screens.UploadSelectionScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.FloBgCream
import com.example.ui.theme.FloBgPistachio
import com.example.ui.theme.FloBgSage
import com.example.ui.theme.FloCardBorder
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveLight
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloTheme
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: FloViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FloTheme {
                FloApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FloApp(viewModel: FloViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    val users by viewModel.users.collectAsStateWithLifecycle()
    val requests by viewModel.requests.collectAsStateWithLifecycle()
    val gigs by viewModel.gigs.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val activeChatId by viewModel.activeChatId.collectAsStateWithLifecycle()
    val selectedProfile by viewModel.selectedUserProfile.collectAsStateWithLifecycle()
    val selectedProjectIndex by viewModel.selectedProjectIndex.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchFilter by viewModel.searchFilter.collectAsStateWithLifecycle()
    val selectedPhotos by viewModel.selectedPhotosForUpload.collectAsStateWithLifecycle()
    val draftTitle by viewModel.draftTitle.collectAsStateWithLifecycle()
    val draftCaption by viewModel.draftCaption.collectAsStateWithLifecycle()
    val draftPlace by viewModel.draftPlace.collectAsStateWithLifecycle()
    val draftGear by viewModel.draftGear.collectAsStateWithLifecycle()
    val draftCategory by viewModel.draftCategory.collectAsStateWithLifecycle()
    val feedbackMessage by viewModel.notificationFeedback.collectAsStateWithLifecycle()

    val pendingRequestsCount = requests.count { it.status == "PENDING" }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(feedbackMessage) {
        feedbackMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearNotificationFeedback()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentScreen != Screen.SPLASH && currentScreen != Screen.WELCOME_1 && currentScreen != Screen.WELCOME_2,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(310.dp)
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                drawerContainerColor = FloBgCream
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // "remove the logo, just write flo only"
                        FloWordmark(color = FloOliveDark, fontSize = 28.sp)

                        IconButton(onClick = { scope.launch { drawerState.close() } }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = FloOliveDark)
                        }
                    }

                    Text(
                        text = "Stories • Memories • Portfolios",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate500,
                        modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
                    )

                    HorizontalDivider(color = FloCardBorder)
                    Spacer(modifier = Modifier.height(10.dp))

                    DrawerMenuRow(
                        icon = Icons.Default.Home,
                        label = "Good Morning Feed",
                        onClick = {
                            viewModel.navigateTo(Screen.HOME)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.Collections,
                        label = "flo Collection",
                        onClick = {
                            viewModel.navigateTo(Screen.EXPLORE)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.AutoAwesome,
                        label = "Event Story Flow",
                        onClick = {
                            viewModel.navigateTo(Screen.CREATE_EVENT_STORY)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.CalendarMonth,
                        label = "Task Schedule & Calendar",
                        onClick = {
                            viewModel.navigateTo(Screen.TASK_SCHEDULE)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.GridView,
                        label = "flo Gallery Grid",
                        onClick = {
                            viewModel.navigateTo(Screen.GALLERY)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.Search,
                        label = "Search Creators & Gear",
                        onClick = {
                            viewModel.navigateTo(Screen.SEARCH)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.ChatBubbleOutline,
                        label = "Collaborations & Inquiries",
                        onClick = {
                            viewModel.navigateTo(Screen.CHAT_LIST)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.ColorLens,
                        label = "Production Moodboard",
                        onClick = {
                            viewModel.navigateTo(Screen.MOODBOARD_COLLAB)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.Notifications,
                        label = "Requests & Offers",
                        badge = if (pendingRequestsCount > 0) "$pendingRequestsCount" else null,
                        onClick = {
                            viewModel.navigateTo(Screen.NOTIFICATIONS)
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerMenuRow(
                        icon = Icons.Default.Person,
                        label = "My Creative Portfolio",
                        onClick = {
                            viewModel.openProfile(null)
                            scope.launch { drawerState.close() }
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    HorizontalDivider(color = FloCardBorder)

                    Text(
                        text = "flo • turning memories into digital stories",
                        fontSize = 11.sp,
                        color = Slate500,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Crossfade(targetState = currentScreen, label = "screenTransition") { screen ->
                    when (screen) {
                        Screen.SPLASH -> {
                            SplashScreen(
                                onTimeout = { viewModel.navigateTo(Screen.WELCOME_1) }
                            )
                        }

                        Screen.WELCOME_1 -> {
                            WelcomeScreen(
                                step = 1,
                                onNext = { viewModel.navigateTo(Screen.WELCOME_2) },
                                onSkip = { viewModel.navigateTo(Screen.LOGIN) }
                            )
                        }

                        Screen.WELCOME_2 -> {
                            WelcomeScreen(
                                step = 2,
                                onNext = { viewModel.navigateTo(Screen.LOGIN) },
                                onSkip = { viewModel.navigateTo(Screen.LOGIN) }
                            )
                        }

                        Screen.LOGIN -> {
                            LoginScreen(
                                onLoginSuccess = { viewModel.navigateTo(Screen.HOME) },
                                onNavigateToRegister = { viewModel.navigateTo(Screen.REGISTER) }
                            )
                        }

                        Screen.REGISTER -> {
                            RegisterScreen(
                                onRegisterSuccess = { viewModel.navigateTo(Screen.HOME) },
                                onNavigateToLogin = { viewModel.navigateTo(Screen.LOGIN) }
                            )
                        }

                        Screen.HOME -> {
                            HomeScreen(
                                posts = posts,
                                creators = users,
                                unreadCount = pendingRequestsCount,
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenProfile = { viewModel.openProfile(it) },
                                onOpenChat = { viewModel.openChatWith(it) },
                                onLikePost = { viewModel.togglePostLike(it) },
                                onSavePost = { viewModel.togglePostSave(it) },
                                onToggleFollow = { viewModel.toggleUserFollow(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.EXPLORE -> {
                            ExploreScreen(
                                creators = users,
                                gigs = gigs,
                                unreadCount = pendingRequestsCount,
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenProfile = { viewModel.openProfile(it) },
                                onApplyGig = { viewModel.applyToGig(it) },
                                onToggleFollow = { viewModel.toggleUserFollow(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.CREATE_EVENT_STORY -> {
                            CreateEventStoryScreen(
                                onNavigate = { viewModel.navigateTo(it) },
                                onClose = { viewModel.navigateTo(Screen.HOME) }
                            )
                        }

                        Screen.TASK_SCHEDULE -> {
                            TaskScheduleScreen(
                                onNavigate = { viewModel.navigateTo(it) },
                                onBack = { viewModel.navigateTo(Screen.CREATE_EVENT_STORY) }
                            )
                        }

                        Screen.GALLERY -> {
                            GalleryScreen(
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.SEARCH -> {
                            SearchScreen(
                                searchQuery = searchQuery,
                                searchFilter = searchFilter,
                                creators = users,
                                gigs = gigs,
                                unreadCount = pendingRequestsCount,
                                onQueryChange = { viewModel.setSearchQuery(it) },
                                onFilterChange = { viewModel.setSearchFilter(it) },
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenProfile = { viewModel.openProfile(it) },
                                onApplyGig = { viewModel.applyToGig(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.UPLOAD_SELECTION -> {
                            UploadSelectionScreen(
                                selectedIndices = selectedPhotos,
                                onTogglePhoto = { viewModel.togglePhotoSelection(it) },
                                onProceedToDetails = { viewModel.navigateTo(Screen.PROJECT_DETAILS) },
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.PROJECT_DETAILS -> {
                            ProjectDetailsScreen(
                                title = draftTitle,
                                caption = draftCaption,
                                place = draftPlace,
                                gear = draftGear,
                                category = draftCategory,
                                onTitleChange = { viewModel.updateDraftTitle(it) },
                                onCaptionChange = { viewModel.updateDraftCaption(it) },
                                onPlaceChange = { viewModel.updateDraftPlace(it) },
                                onGearChange = { viewModel.updateDraftGear(it) },
                                onCategoryChange = { viewModel.updateDraftCategory(it) },
                                onPublish = { viewModel.publishDraftProject() },
                                onBack = { viewModel.navigateTo(Screen.UPLOAD_SELECTION) }
                            )
                        }

                        Screen.CHAT_LIST -> {
                            ChatListScreen(
                                creators = users,
                                unreadCount = pendingRequestsCount,
                                onSelectChat = { viewModel.openChatWith(it) },
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.CHAT_BOX -> {
                            ChatBoxScreen(
                                collaboratorHandle = activeChatId,
                                creators = users,
                                messages = chatMessages,
                                onSendMessage = { viewModel.sendMessage(it) },
                                onBack = { viewModel.navigateTo(Screen.CHAT_LIST) }
                            )
                        }

                        Screen.PROFILE -> {
                            ProfileScreen(
                                user = selectedProfile,
                                selectedProjectIndex = selectedProjectIndex,
                                unreadCount = pendingRequestsCount,
                                onSelectProjectIndex = { viewModel.selectProjectIndex(it) },
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenChat = { viewModel.openChatWith(it) },
                                onToggleFollow = { viewModel.toggleUserFollow(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.NOTIFICATIONS -> {
                            NotificationsScreen(
                                requests = requests,
                                onAcceptRequest = { viewModel.acceptRequest(it.id) },
                                onDeclineRequest = { viewModel.declineRequest(it.id) },
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenProfile = { viewModel.openProfile(null) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }

                        Screen.MOODBOARD_COLLAB -> {
                            MoodboardCollabScreen(
                                onNavigate = { viewModel.navigateTo(it) },
                                onOpenDrawerMenu = { scope.launch { drawerState.open() } }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerMenuRow(
    icon: ImageVector,
    label: String,
    badge: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = FloOliveDark,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = FloOliveDark
            )
        }

        if (badge != null) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = FloOliveMedium,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = badge,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}

// Kept for backward compatibility with GreetingScreenshotTest and preview rules
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme { Greeting("Android") }
}
