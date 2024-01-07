package com.thebrownfoxx.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IconTextCard(
    imageVector: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
    active: Boolean = false,
    topActive: Boolean = false,
    bottomActive: Boolean = false,
) {
    val topCornerRadius by animateDpAsState(
        targetValue = when {
            active && topActive -> 0.dp
            active -> 8.dp
            else -> 0.dp
        },
        label = "topCornerRadius",
    )
    val bottomCornerRadius by animateDpAsState(
        targetValue = when {
            active && bottomActive -> 0.dp
            active -> 8.dp
            else -> 0.dp
        },
        label = "bottomCornerRadius",
    )
    val containerColor by animateColorAsState(
        targetValue = when {
            active -> colorScheme.primaryContainer
            else -> colorScheme.background
        },
        label = "containerColor",
    )
    val contentColor by animateColorAsState(
        targetValue = when {
            active -> colorScheme.onPrimaryContainer
            else -> colorScheme.onBackground
        },
        label = "contentColor",
    )

    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = shapes.medium.copy(
            topStart = CornerSize(topCornerRadius),
            topEnd = CornerSize(topCornerRadius),
            bottomStart = CornerSize(bottomCornerRadius),
            bottomEnd = CornerSize(bottomCornerRadius),
        ),
        onClick = { if (onClick != null) onClick() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = text,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
fun ImageTextCardPreview() {
    IconTextCard(
        imageVector = Icons.Default.AccountCircle,
        text = "Trident",
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}

@Preview
@Composable
fun ImageTextCardListPreview() {
    val active = remember { mutableStateListOf(false, false, false, false, false) }

    LazyColumn {
        itemsIndexed(active) { index, currentActive ->
            val beforeActive = active.getOrElse(index - 1) { false }
            val afterActive = active.getOrElse(index + 1) { false }

            IconTextCard(
                imageVector = Icons.Default.AccountCircle,
                text = "Trident",
                onClick = { active[index] = !currentActive },
                modifier = Modifier.fillMaxWidth(),
                active = currentActive,
                topActive = beforeActive,
                bottomActive = afterActive,
            )
        }
    }
}