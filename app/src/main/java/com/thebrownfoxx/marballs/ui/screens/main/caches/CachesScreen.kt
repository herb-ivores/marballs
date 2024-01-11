package com.thebrownfoxx.marballs.ui.screens.main.caches
// TODO: Rename namespace to com.herbivores.marballs

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.components.extension.plus
import com.thebrownfoxx.components.extension.top
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Loadable
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.ui.components.FailureScreen
import com.thebrownfoxx.marballs.ui.components.LoadingScreen
import com.thebrownfoxx.marballs.ui.components.SearchBar
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CachesScreen(
    caches: Loadable<List<CacheInfo>>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCacheSelect: (CacheInfo) -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (caches) {
        is Loadable.Loading -> LoadingScreen(modifier = modifier.systemBarsPadding())
        is Loadable.Success -> {
            Scaffold(modifier = modifier) { contentPadding ->
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
                        items = caches.data,
                        key = { it.id },
                    ) { cache ->
                        CompactCacheCard(cache = cache, onClick = { onCacheSelect(cache) })
                    }
                    Log.d("CachesScreen", "Caches size: ${caches.data.size}")
                }
            }
        }
        is Loadable.Failure -> FailureScreen(
            onReload = onReload,
            modifier = modifier.systemBarsPadding(),
        )
    }
}

@Preview
@Composable
fun CachesScreenPreview() {
    val author = User(uid = "1", email = "jonelespiritu@fuckmail.com")

    AppTheme {
        CachesScreen(
            caches = Loadable.Success(
                listOf(
                    CacheInfo(
                        id = "1",
                        name = "Cache 1",
                        description = "This is a cache",
                        location = "Location 1",
                        distance = 1.0.meters,
                        author = author,
                        coordinates = Location(19.2132,121.3242)
                    ),
                    CacheInfo(
                        id = "2",
                        name = "Cache 2",
                        description = "This is a cache",
                        location = "Location 2",
                        distance = 2.0.meters,
                        author = author,
                        coordinates = Location(18.2132,122.3242)
                    ),
                    CacheInfo(
                        id = "3",
                        name = "Cache 3",
                        description = "This is a cache",
                        location = "Location 3",
                        distance = 3.0.meters,
                        author = author,
                        coordinates = Location(17.2132,120.3242)
                    ),
                    CacheInfo(
                        id = "4",
                        name = "Cache 4",
                        description = "This is a cache",
                        location = "Location 4",
                        distance = 4.0.meters,
                        author = author,
                        coordinates = Location(15.2132,115.3242)
                    ),
                )
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onCacheSelect = {},
            onReload = {},
        )
    }
}