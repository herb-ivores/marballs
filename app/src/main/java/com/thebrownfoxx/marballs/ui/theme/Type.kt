package com.thebrownfoxx.marballs.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.marballs.R

private val inter = FontFamily(Font(R.font.inter))

// Set of Material typography styles to start with
private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Medium,
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Medium,
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Medium,
    ),
    labelLarge = defaultTypography.labelLarge.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Thin,
    ),
    labelMedium = defaultTypography.labelMedium.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Thin,
    ),
    labelSmall = defaultTypography.labelSmall.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Thin,
    ),
)


@Preview
@Composable
fun TypographyPreview() {
    AppTheme {
        Column {
            Text(text = "Display Large", style = typography.displayLarge)
            Text(text = "Display Medium", style = typography.displayMedium)
            Text(text = "Display Small", style = typography.displaySmall)
            Text(text = "Headline Large", style = typography.headlineLarge)
            Text(text = "Headline Medium", style = typography.headlineMedium)
            Text(text = "Headline Small", style = typography.headlineSmall)
            Text(text = "Title Large", style = typography.titleLarge)
            Text(text = "Title Medium", style = typography.titleMedium)
            Text(text = "Title Small", style = typography.titleSmall)
            Text(text = "Body Large", style = typography.bodyLarge)
            Text(text = "Body Medium", style = typography.bodyMedium)
            Text(text = "Body Small", style = typography.bodySmall)
            Text(text = "Label Large", style = typography.labelLarge)
            Text(text = "Label Medium", style = typography.labelMedium)
            Text(text = "Label Small", style = typography.labelSmall)
        }
    }
}