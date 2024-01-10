package com.thebrownfoxx.marballs.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.marballs.application
import com.thebrownfoxx.marballs.ui.screens.destinations.MainDestination
import com.thebrownfoxx.marballs.ui.screens.destinations.SignupDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun Login(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = viewModel { LoginViewModel(application.authentication) },
) {
    with(viewModel) {
        val email by email.collectAsStateWithLifecycle()
        val password by password.collectAsStateWithLifecycle()
        val loading by loading.collectAsStateWithLifecycle()

        val loggedIn by loggedIn.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (loggedIn) {
                navigator.navigate(MainDestination)
            }
        }

        LoginScreen(
            email = email,
            password = password,
            onEmailChange = ::setEmail,
            onPasswordChange = ::setPassword,
            onLogin = ::login,
            onSignup = { navigator.navigate(SignupDestination) },
            loading = loading,
            errors = errors,
        )
    }
}