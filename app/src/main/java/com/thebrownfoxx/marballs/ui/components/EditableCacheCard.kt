package com.thebrownfoxx.marballs.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@Composable
fun EditableCacheCard(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    saveButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
        ) {
            VerticalSpacer(height = 16.dp)
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                TextField(
                    label = { Text(text = "Name") },
                    value = name,
                    onValueChange = onNameChange,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                VerticalSpacer(height = 16.dp)
                TextField(
                    label = { Text(text = "Description") },
                    value = description,
                    onValueChange = onDescriptionChange,
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            VerticalSpacer(height = 16.dp)
            saveButton()
            VerticalSpacer(height = 8.dp)
        }
    }
}

@Preview
@Composable
fun EditableCacheCardPreview() {
    AppTheme {
        EditableCacheCard(
            name = "Cache Name",
            onNameChange = {},
            description = "Cache Description",
            onDescriptionChange = {},
            saveButton = {
                FilledButton(
                    text = "Save",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            modifier = Modifier.padding(16.dp),
        )
    }
}