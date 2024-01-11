package com.thebrownfoxx.marballs.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@Composable
fun FailureScreen(
    onReload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleLarge,
        )
        FilledButton(
            icon = Icons.TwoTone.Refresh,
            text = "Reload",
            onClick = onReload,
        )
    }
}

@Preview
@Composable
fun FailureScreenPreview() {
    AppTheme {
        FailureScreen(onReload = {})
    }
}