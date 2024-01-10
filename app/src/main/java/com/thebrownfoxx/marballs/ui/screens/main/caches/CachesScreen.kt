package com.thebrownfoxx.marballs.ui.screens.main.caches
// TODO: Rename namespace to com.herbivores.marballs

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
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CachesScreen(
    caches: List<CacheInfo>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCacheSelect: (CacheInfo) -> Unit,
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
                items = caches,
                key = { it.id },
            ) { cache ->
                CompactCacheCard(cache = cache, onClick = { onCacheSelect(cache) })
            }
        }
    }
}

@Preview
@Composable
fun CachesScreenPreview() {
    val author = User(uid = "1", email = "jonelespiritu@fuckmail.com")

    AppTheme {
        CachesScreen(
            caches = listOf(
                CacheInfo(
                    id = "1",
                    name = "Cache 1",
                    description = "This is a cache",
                    location = "Location 1",
                    distance = 1.0.meters,
                    author = author,
                ),
                CacheInfo(
                    id = "2",
                    name = "Cache 2",
                    description = "This is a cache",
                    location = "Location 2",
                    distance = 2.0.meters,
                    author = author,
                ),
                CacheInfo(
                    id = "3",
                    name = "Cache 3",
                    description = "This is a cache",
                    location = "Location 3",
                    distance = 3.0.meters,
                    author = author,
                ),
                CacheInfo(
                    id = "4",
                    name = "Cache 4",
                    description = "This is a cache",
                    location = "Location 4",
                    distance = 4.0.meters,
                    author = author,
                ),
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onCacheSelect = {},
        )
    }
}