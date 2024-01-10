package com.thebrownfoxx.marballs.ui.screens.main.finds

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.extension.plus
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.FindInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import java.time.Instant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FindsScreen(
    finds: List<FindInfo>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFindSelect: (FindInfo) -> Unit,
    onunmarkFindAsFound: (FindInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding + PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            stickyHeader {
                TextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.TwoTone.Search,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    },
                    placeholder = { Text(text = "Search caches") },
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
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            items(
                items = finds,
                key = { it.id },
            ) { find ->
                FindCard(
                    find = find,
                    onClick = { onFindSelect(find) },
                    onUnfind = { onunmarkFindAsFound(find) },
                )
            }
        }
    }
}

@Preview
@Composable
fun FindsScreenPreview() {
    FindsScreen(
        finds = listOf(
            FindInfo(
                id = "fiehfie",
                cache = CacheInfo(
                    id = "uy3gur",
                    name = "Cache name",
                    description = "Cache description",
                    coordinates = Location(0.0, 0.0),
                    location = "Cache location",
                    distance = 100.0.meters,
                    author = User("uhu", "Hello"),
                ),
                found = Instant.now(),
                finder = User("ufhe", "fiuehfi"),
            ),
            FindInfo(
                id = "4t44t4t",
                cache = CacheInfo(
                    id = "g4g4g34g",
                    name = "Cache name",
                    description = "Cache description",
                    coordinates = Location(0.0, 0.0),
                    location = "Cache location",
                    distance = 100.0.meters,
                    author = User("uhu", "Hello"),
                ),
                found = Instant.now(),
                finder = User("ufhe", "fiuehfi"),
            ),
            FindInfo(
                id = "g4g4g4g",
                cache = CacheInfo(
                    id = "g4g4g34g",
                    name = "Cache name",
                    description = "Cache description",
                    coordinates = Location(0.0, 0.0),
                    location = "Cache location",
                    distance = 100.0.meters,
                    author = User("uhu", "Hello"),
                ),
                found = Instant.now(),
                finder = User("ufhe", "fiuehfi"),
            ),
        ),
        searchQuery = "",
        onSearchQueryChange = {},
        onFindSelect = {},
        onunmarkFindAsFound = {},
    )
}