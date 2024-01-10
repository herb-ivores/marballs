package com.thebrownfoxx.marballs.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.HorizontalSpacer

@Composable
fun Info(
    icon: ImageVector,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(imageVector = icon, contentDescription = null)
        HorizontalSpacer(width = 16.dp)
        Text(
            text = value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}