package com.thebrownfoxx.marballs.ui.screens.main.caches
// TODO: Rename namespace to com.herbivores.marballs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.components.extension.plus
import com.thebrownfoxx.components.extension.top
import com.thebrownfoxx.marballs.R
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.ui.components.EmptyScreen
import com.thebrownfoxx.marballs.ui.components.SearchBar
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CachesScreen(
    caches: List<CacheInfo>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCacheSelect: (CacheInfo) -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = null,
                        )
                        HorizontalSpacer(width = 8.dp)
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                    append("Mar")
                                }
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("balls")
                                }
                            }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        imageVector = Icons.TwoTone.Refresh,
                        contentDescription = null,
                        onClick = onReload,
                    )
                },
                scrollBehavior = topAppBarScrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                ),
            )
        },
        modifier = modifier,
    ) { contentPadding ->
        if (caches.isNotEmpty()) {
            val topPadding = contentPadding.top + 16.dp
            LazyColumn(
                contentPadding = contentPadding + PaddingValues(16.dp) -
                        PaddingValues(top = topPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                stickyHeader {
                    Column {
                        VerticalSpacer(height = topPadding)
                        SearchBar(
                            label = "Search caches",
                            searchQuery = searchQuery,
                            onSearchQueryChange = onSearchQueryChange,
                        )
                    }
                }
                items(
                    items = caches,
                    key = { it.id },
                ) { cache ->
                    CompactCacheCard(cache = cache, onClick = { onCacheSelect(cache) })
                }
            }
        } else {
            EmptyScreen(modifier = modifier.padding(contentPadding))
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
                    coordinates = Location(19.2132, 121.3242)
                ),
                CacheInfo(
                    id = "2",
                    name = "Cache 2",
                    description = "This is a cache",
                    location = "Location 2",
                    distance = 2.0.meters,
                    author = author,
                    coordinates = Location(18.2132, 122.3242)
                ),
                CacheInfo(
                    id = "3",
                    name = "Cache 3",
                    description = "This is a cache",
                    location = "Location 3",
                    distance = 3.0.meters,
                    author = author,
                    coordinates = Location(17.2132, 120.3242)
                ),
                CacheInfo(
                    id = "4",
                    name = "Cache 4",
                    description = "This is a cache",
                    location = "Location 4",
                    distance = 4.0.meters,
                    author = author,
                    coordinates = Location(15.2132, 115.3242)
                ),
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onCacheSelect = {},
            onReload = {},
        )
    }
}