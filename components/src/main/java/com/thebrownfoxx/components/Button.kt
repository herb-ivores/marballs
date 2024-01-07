package com.thebrownfoxx.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun ButtonContent(
    text: String,
    icon: ImageVector? = null,
    iconContentDescription: String? = icon?.let { text },
) {
    if (icon != null) {
        Icon(imageVector = icon, contentDescription = iconContentDescription)
        HorizontalSpacer(width = 8.dp)
    }
    Text(text = text)
}

@Composable
fun FilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconContentDescription: String? = icon?.let { text },
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        ButtonContent(
            text = text,
            icon = icon,
            iconContentDescription = iconContentDescription,
        )
    }
}

@Composable
fun FilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconContentDescription: String? = icon?.let { text },
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.filledTonalShape,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
    elevation: ButtonElevation? = ButtonDefaults.filledTonalButtonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        ButtonContent(
            text = text,
            icon = icon,
            iconContentDescription = iconContentDescription,
        )
    }
}

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        Text(text = text)
    }
}

@Composable
fun IconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}

@Composable
fun FilledIconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}

@Composable
fun FilledTonalIconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}

@Preview
@Composable
fun FilledButtonPreview() {
    FilledButton(
        text = "Like",
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview
@Composable
fun FilledButtonWithIconPreview() {
    FilledButton(
        icon = Icons.Default.ThumbUp,
        text = "Like",
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview
@Composable
fun FilledTonalButtonPreview() {
    FilledTonalButton(
        text = "Like",
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview
@Composable
fun FilledTonalButtonWithIconPreview() {
    FilledTonalButton(
        icon = Icons.Default.ThumbUp,
        text = "Like",
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview
@Composable
fun TextButtonPreview() {
    TextButton(
        text = "Like",
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview
@Composable
fun IconButtonPreview() {
    IconButton(
        imageVector = Icons.Default.ThumbUp,
        contentDescription = null,
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview
@Composable
fun FilledIconButtonPreview() {
    FilledIconButton(
        imageVector = Icons.Default.ThumbUp,
        contentDescription = null,
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview
@Composable
fun FilledTonalIconButtonPreview() {
    FilledTonalIconButton(
        imageVector = Icons.Default.ThumbUp,
        contentDescription = null,
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}