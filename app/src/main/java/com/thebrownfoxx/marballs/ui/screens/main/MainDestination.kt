package com.thebrownfoxx.marballs.ui.screens.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.components.extension.Zero
import com.thebrownfoxx.marballs.application
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.ui.extensions.SnackbarHost
import com.thebrownfoxx.marballs.ui.extensions.rememberSnackbarHostState
import com.thebrownfoxx.marballs.ui.screens.destinations.AddCacheDestination
import com.thebrownfoxx.marballs.ui.screens.destinations.ChangePasswordDestination
import com.thebrownfoxx.marballs.ui.screens.destinations.EditCacheDestination
import com.thebrownfoxx.marballs.ui.screens.main.caches.CachesScreen
import com.thebrownfoxx.marballs.ui.screens.main.map.MapScreen
import com.thebrownfoxx.marballs.ui.screens.main.profile.ProfileScreen

@RootNavGraph
@Destination
@Composable
fun Main(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = viewModel {
        MainViewModel(
            application.authentication,
            application.locationProvider,
            application.cacheRepository,
            application.cacheInfoProvider,
            application.findsRepository,
            application.findInfoProvider,
        )
    },
) {
    val activity = (LocalContext.current as? Activity)

    BackHandler {
        activity?.moveTaskToBack(true)
    }

    with(viewModel) {
        val currentUser by currentUser.collectAsStateWithLifecycle()

        val currentScreen by currentScreen.collectAsStateWithLifecycle()

        val location by location.collectAsStateWithLifecycle()
        val selectedCache by selectedCache.collectAsStateWithLifecycle()
        val allowCacheEdit by allowCacheEdit.collectAsStateWithLifecycle()
        val selectedCacheFound by selectedCacheFound.collectAsStateWithLifecycle()
        val deleteDialogVisible by deleteDialogVisible.collectAsStateWithLifecycle()

        val caches by caches.collectAsStateWithLifecycle()
        val cachesSearchQuery by cachesSearchQuery.collectAsStateWithLifecycle()

        val finds by finds.collectAsStateWithLifecycle()
        val findsSearchQuery by findsSearchQuery.collectAsStateWithLifecycle()

        val loggedIn by loggedIn.collectAsStateWithLifecycle()

        val loading by loading.collectAsStateWithLifecycle()

        val disabledFind by disabledFind.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (!loggedIn) {
                navigator.navigateUp()
            }
        }

        Scaffold(
            contentWindowInsets = WindowInsets.Zero,
            snackbarHost = {
                SnackbarHost(state = rememberSnackbarHostState(messages = errors))
            },
            bottomBar = {
                BottomBar(
                    currentScreen = currentScreen,
                    onNavigateTo = ::navigateTo,
                )
            },
        ) { contentPadding ->
            when (currentScreen) {
                MainScreen.Map -> MapScreen(
                    location = location,
                    caches = caches,
                    selectedCache = selectedCache,
                    onSelectCache = ::selectCache,
                    allowCacheEdit = allowCacheEdit,
                    selectedCacheFound = selectedCacheFound,
                    onMarkSelectedCacheAsFound = ::markSelectedCacheAsFound,
                    onUnmarkSelectedCacheAsFound = ::unmarkSelectedCacheAsFound,
                    onEditCache = {
                        navigator.navigate(EditCacheDestination(selectedCache?.id!!))
                    },
                    onResetLocation = ::resetLocation,
                    onAddCache = { navigator.navigate(AddCacheDestination) },
                    deleteDialogVisible = deleteDialogVisible,
                    onInitiateDeleteCache = ::initiateDeleteCache,
                    onCancelDeleteCache = ::cancelDeleteCache,
                    onDeleteCache = ::deleteCache,
                    modifier = Modifier.padding(contentPadding),
                    loading = loading,
                )

                MainScreen.Caches -> CachesScreen(
                    caches = caches,
                    searchQuery = cachesSearchQuery,
                    onSearchQueryChange = ::setCachesSearchQuery,
                    onCacheSelect = ::selectCache,
                    onReload = ::reload,
                    modifier = Modifier.padding(contentPadding),
                )

                MainScreen.Profile -> ProfileScreen(
                    currentUser = currentUser ?: User("", ""),
                    finds = finds,
                    searchQuery = findsSearchQuery,
                    onSearchQueryChange = ::setFindsSearchQuery,
                    onFindSelect = ::selectFind,
                    onUnmarkFindAsFound = ::unmarkFindAsFound,
                    onReload = ::reload,
                    onChangePassword = { navigator.navigate(ChangePasswordDestination) },
                    onLogout = ::logout,
                    modifier = Modifier.padding(contentPadding),
                    disabledFind = disabledFind,
                )
            }
        }
    }
}