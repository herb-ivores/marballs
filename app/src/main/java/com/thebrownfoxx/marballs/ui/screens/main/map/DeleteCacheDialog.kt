package com.thebrownfoxx.marballs.ui.screens.main.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@Composable
fun DeleteCacheDialog(
    visible: Boolean,
    cacheName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (visible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            icon = {
                Icon(imageVector = Icons.TwoTone.DeleteForever, contentDescription = null)
            },
            title = {
                Text(
                    text = "Delete book",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            text = {
                Text(text = "Are you sure you want to delete $cacheName? It will be gone forever.")
            },
            dismissButton = {
                TextButton(
                    text = "No",
                    onClick = onDismiss,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Yes",
                    onClick = onConfirm,
                )
            },
        )
    }
}