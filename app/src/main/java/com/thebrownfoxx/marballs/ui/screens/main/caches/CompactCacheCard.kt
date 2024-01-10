package com.thebrownfoxx.marballs.ui.screens.main.caches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material.icons.twotone.Straighten
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.ui.components.Info
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@Composable
fun CompactCacheCard(
    cache: CacheInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = cache.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = cache.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalSpacer(height = 16.dp)
            Info(
                icon = Icons.TwoTone.LocationOn,
                value = cache.location,
            )
            VerticalSpacer(height = 8.dp)
            Info(
                icon = Icons.TwoTone.Straighten,
                value = "${cache.distance}",
            )
        }
    }
}

@Preview
@Composable
fun CompactCacheCardPreview() {
    AppTheme {
        CompactCacheCard(
            cache = CacheInfo(
                id = "1",
                name = "Huge Booty",
                description = "Sussy baka hiding in the bushes.",
                location = "Area 69",
                distance = 69.0.meters,
                author = User(uid = "1", email = "jonelespiritu@fuckers-online.io"),
                coordinates = Location(19.2132,121.3242)
            ),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun CompactCacheCardLongTextsPreview() {
    AppTheme {
        CompactCacheCard(
            cache = CacheInfo(
                id = "1",
                name = "A quick sussy fox fucking a lazy dog",
                location = "69th Floor, Area 69, Sussy Baka City, Sussy Baka Country, Sussy Baka Planet, Sussy Baka Galaxy, Sussy Baka Universe, Sussy Baka Multiverse, Sussy Baka Omniverse, Sussy Baka Megaverse, Sussy Baka Hyperverse, Sussy Baka Ultraverse, Sussy Baka Xenoverse, Sussy Baka Archverse, Sussy Baka Metaverse, Sussy Baka Ayashaverse, Sussy Baka Ayashiverse, Sussy Baka Ayashihyperverse, Sussy Baka Ayashiultraverse, Sussy Baka Ayashixenoverse, Sussy Baka Ayashiarchverse, Sussy Baka Ayashimetaverse, Sussy Baka Ayashimegaverse, Sussy Baka Ayashiomniverse, Sussy Baka",
                description = "These are model dolls hidden in a chest. Please stop cumming on them. It's very difficult having to go back and clean all the sticky gunk you all left on my poor dolls.",
                distance = 69.0.meters,
                author = User(uid = "1", email = "jonelespiritu@fuckers-online.io"),
                coordinates = Location(19.2132,121.3242)
            ),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}