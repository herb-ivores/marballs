package com.thebrownfoxx.marballs.ui.screens.main.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Logout
import androidx.compose.material.icons.twotone.Password
import androidx.compose.material.icons.twotone.Person
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.components.extension.plus
import com.thebrownfoxx.components.extension.top
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.FindInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.ui.components.EmptyScreen
import com.thebrownfoxx.marballs.ui.components.SearchBar
import com.thebrownfoxx.marballs.ui.theme.AppTheme
import java.time.Instant

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    currentUser: User,
    finds: List<FindInfo>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFindSelect: (FindInfo) -> Unit,
    onUnmarkFindAsFound: (FindInfo) -> Unit,
    onReload: () -> Unit,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit,
    disabledFind: FindInfo?,
    modifier: Modifier = Modifier,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.TwoTone.Person,
                            contentDescription = null,
                        )
                        HorizontalSpacer(width = 8.dp)
                        Text(text = currentUser.username)
                    }
                },
                navigationIcon = {
                    IconButton(
                        imageVector = Icons.TwoTone.Refresh,
                        contentDescription = null,
                        onClick = onReload,
                    )
                },
                actions = {
                    IconButton(
                        imageVector = Icons.TwoTone.Password,
                        contentDescription = null,
                        onClick = onChangePassword,
                    )
                    IconButton(
                        imageVector = Icons.AutoMirrored.TwoTone.Logout,
                        contentDescription = null,
                        onClick = onLogout,
                    )
                },
                scrollBehavior = topAppBarScrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                ),
            )
        },
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
    ) { contentPadding ->
        val topPadding = contentPadding.top + 16.dp

        if (finds.isNotEmpty()) {
            LazyColumn(
                contentPadding = contentPadding + PaddingValues(16.dp) -
                        PaddingValues(top = topPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                stickyHeader {
                    Column {
                        VerticalSpacer(height = topPadding)
                        SearchBar(
                            label = "Search finds",
                            searchQuery = searchQuery,
                            onSearchQueryChange = onSearchQueryChange,
                        )
                    }
                }
                items(
                    items = finds,
                    key = { it.id },
                ) { find ->
                    FindCard(
                        find = find,
                        onClick = { onFindSelect(find) },
                        onUnfind = { onUnmarkFindAsFound(find) },
                        loading = find == disabledFind,
                    )
                }
            }
        } else {
            EmptyScreen(modifier = modifier.padding(contentPadding))
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            currentUser = User("ufhe", "fiuehfi"),
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
            onUnmarkFindAsFound = {},
            onReload = {},
            onChangePassword = {},
            onLogout = {},
            disabledFind = null
        )
    }
}