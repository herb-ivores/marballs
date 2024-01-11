package com.thebrownfoxx.marballs.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.IndeterminateCheckBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.VerticalSpacer

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Icon(imageVector = Icons.TwoTone.IndeterminateCheckBox, contentDescription = null)
        VerticalSpacer(height = 8.dp)
        Text(
            text = "There is nothing here",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}