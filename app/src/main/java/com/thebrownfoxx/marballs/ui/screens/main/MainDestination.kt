package com.thebrownfoxx.marballs.ui.screens.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.components.extension.Zero
import com.thebrownfoxx.marballs.application
import com.thebrownfoxx.marballs.ui.screens.destinations.AddCacheDestination
import com.thebrownfoxx.marballs.ui.screens.destinations.EditCacheDestination
import com.thebrownfoxx.marballs.ui.screens.main.caches.CachesScreen
import com.thebrownfoxx.marballs.ui.screens.main.map.MapScreen

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
        )
    },
) {
    with(viewModel) {
        val currentScreen by currentScreen.collectAsStateWithLifecycle()

        val currentLocation by currentLocation.collectAsStateWithLifecycle()
        val selectedCache by selectedCache.collectAsStateWithLifecycle()
        val allowCacheEdit by allowCacheEdit.collectAsStateWithLifecycle()
        val caches by caches.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()

        val loggedIn by loggedIn.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (!loggedIn) {
                navigator.navigateUp()
            }
        }

        Scaffold(
            contentWindowInsets = WindowInsets.Zero,
            bottomBar = {
                BottomBar(
                    currentScreen = currentScreen,
                    onNavigateTo = ::navigateTo,
                )
            },
        ) { contentPadding ->
            when (currentScreen) {
                MainScreen.Map -> MapScreen(
                    currentLocation = currentLocation,
                    selectedCache = selectedCache,
                    allowCacheEdit = allowCacheEdit,
                    onEditCache = {
                        navigator.navigate(EditCacheDestination(selectedCache?.id!!))
                    },
                    onResetLocation = ::resetLocation,
                    onAddCache = { navigator.navigate(AddCacheDestination) },
                    modifier = Modifier.padding(contentPadding),
                )
                MainScreen.Caches -> CachesScreen(
                    caches = caches ?: emptyList(),
                    searchQuery = searchQuery,
                    onSearchQueryChange = ::setSearchQuery,
                    onCacheSelect = ::selectCache,
                    modifier = Modifier.padding(contentPadding),
                )
                else -> {}
            }
        }
    }
}