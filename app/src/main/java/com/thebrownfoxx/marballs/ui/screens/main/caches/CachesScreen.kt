package com.thebrownfoxx.marballs.ui.screens.main.caches

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.marballs.domain.CacheInfo

@Composable
fun CachesScreen(
    caches: List<CacheInfo>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCacheSelect: (CacheInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
    ) {

    }
}