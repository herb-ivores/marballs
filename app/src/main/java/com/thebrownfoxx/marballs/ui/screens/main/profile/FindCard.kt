package com.thebrownfoxx.marballs.ui.screens.main.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledTonalButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.FindInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.ui.components.Info
import com.thebrownfoxx.marballs.ui.theme.AppTheme
import java.time.Instant

@Composable
fun FindCard(
    find: FindInfo,
    onClick: () -> Unit,
    onUnfind: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            VerticalSpacer(height = 16.dp)
            Text(
                text = find.cache.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalSpacer(height = 8.dp)
            Text(text = find.cache.description)
            VerticalSpacer(height = 16.dp)
            Info(
                icon = Icons.TwoTone.LocationOn,
                value = find.cache.location,
            )
            VerticalSpacer(height = 16.dp)
            FilledTonalButton(
                icon = Icons.TwoTone.Close,
                iconContentDescription = null,
                text = "Not found",
                onClick = onUnfind,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(height = 8.dp)
        }
    }
}

@Preview
@Composable
fun FindCardPreview() {
    AppTheme {
        FindCard(
            find = FindInfo(
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
            onClick = {},
            onUnfind = {},
        )
    }
}