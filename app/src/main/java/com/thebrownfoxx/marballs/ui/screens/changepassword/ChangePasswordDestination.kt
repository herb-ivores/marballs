package com.thebrownfoxx.marballs.ui.screens.changepassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.marballs.application
import com.thebrownfoxx.marballs.domain.User

@Destination
@Composable
fun ChangePassword(
    navigator: DestinationsNavigator,
    viewModel: ChangePasswordViewModel = viewModel { ChangePasswordViewModel(application.authentication) },
) {
    with(viewModel) {
        val loggedIn by loggedIn.collectAsStateWithLifecycle()
        val currentUser by currentUser.collectAsStateWithLifecycle()
        val oldPassword by oldPassword.collectAsStateWithLifecycle()
        val newPassword by newPassword.collectAsStateWithLifecycle()
        val repeatPassword by repeatPassword.collectAsStateWithLifecycle()
        val loading by loading.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (!loggedIn) {
                navigator.navigateUp()
            }
        }

        LaunchedEffect(success) {
            success.collect { navigator.navigateUp() }
        }

        ChangePasswordScreen(
            currentUser = currentUser ?: User("", ""),
            oldPassword = oldPassword,
            onOldPassswordChange = { setOldPassword(it) },
            newPassword = newPassword,
            onNewPasswordChange = { setNewPassword(it) },
            repeatPassword = repeatPassword,
            onRepeatPasswordChange = { setRepeatPassword(it) },
            onConfirm = { changePassword() },
            onNavigateUp = { navigator.navigateUp() },
            loading = loading,
            errors = errors,
        )
    }
}