package com.thebrownfoxx.marballs.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.marballs.application

@RootNavGraph
@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = viewModel {
        HomeViewModel(
            application.authService,
            application.mapService,
        )
    },
) {
    with(viewModel) {
        val currentLocation by currentLocation.collectAsStateWithLifecycle()

        val loggedIn by loggedIn.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (!loggedIn) {
                navigator.navigateUp()
            }
        }

        HomeScreen(
            currentLocation = currentLocation,
            onLogout = ::logout,
        )
    }
}