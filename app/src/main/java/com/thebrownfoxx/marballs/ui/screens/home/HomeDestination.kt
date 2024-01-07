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
import com.thebrownfoxx.marballs.ui.screens.destinations.LoginDestination

@RootNavGraph
@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = viewModel { HomeViewModel(application.authService) },
) {
    with(viewModel) {
        val loggedIn by loggedIn.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (!loggedIn) {
                navigator.navigateUp()
            }
        }

        HomeScreen(
            onLogout = ::logout,
        )
    }
}