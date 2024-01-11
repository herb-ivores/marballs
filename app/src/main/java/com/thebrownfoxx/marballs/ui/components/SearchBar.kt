package com.thebrownfoxx.marballs.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.IconButton

@Composable
fun SearchBar(
    label: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.TwoTone.Search,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp),
            )
        },
        placeholder = { Text(text = label) },
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    imageVector = Icons.TwoTone.Clear,
                    contentDescription = null,
                    onClick = { onSearchQueryChange("") },
                    modifier = Modifier.padding(end = 4.dp),
                )
            }
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = modifier.fillMaxWidth(),
    )
}