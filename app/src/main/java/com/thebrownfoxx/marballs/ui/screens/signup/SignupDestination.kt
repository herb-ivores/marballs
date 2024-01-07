package com.thebrownfoxx.marballs.ui.screens.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.marballs.application

@Destination
@Composable
fun Signup(
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel = viewModel { SignupViewModel(application.authService) },
) {
    with(viewModel) {
        val email by email.collectAsStateWithLifecycle()
        val password by password.collectAsStateWithLifecycle()
        val repeatPassword by repeatPassword.collectAsStateWithLifecycle()
        val loading by loading.collectAsStateWithLifecycle()

        val loggedIn by loggedIn.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (loggedIn) {
                // TODO: navigate to home instead
                navigator.navigateUp()
            }
        }

        SignupScreen(
            email = email,
            onEmailChange = ::setEmail,
            password = password,
            onPasswordChange = ::setPassword,
            repeatPassword = repeatPassword,
            onRepeatPasswordChange = ::setRepeatPassword,
            onSignUp = ::signUp,
            loading = loading,
            errors = errors,
            onNavigateUp = { navigator.navigateUp() },
        )
    }
}