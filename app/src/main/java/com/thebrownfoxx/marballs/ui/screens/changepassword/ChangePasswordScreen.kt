package com.thebrownfoxx.marballs.ui.screens.changepassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChangePasswordScreen(
    password: String,
    onPasswordChange: (String) -> Unit,
    repeatPassword: String,
    onRepeatPasswordChange: (String) -> Unit,
    onConfirm: () -> Unit,
    errors: List<String>,
    modifier: Modifier = Modifier,
) {

}