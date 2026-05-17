package com.talkfrly.multiplatform.ui.theme

import androidx.compose.ui.graphics.Color

data class TalkfrlyColors(
    val primary: Color,
    val primary60: Color,
    val primary20: Color,

    val body: Color,
    val bodyMuted: Color,

    val accent: Color,

    val background: Color,
    val backgroundDarker: Color,
    val backgroundLighter: Color,

    val surface: Color,

    val error: Color,
    val error60: Color,
    val error20: Color,

    val black: Color,
    val white: Color,
)

fun talkfrlyLightThemeColors(): TalkfrlyColors = TalkfrlyColors(
    primary = Color.hsl(hue = 65f, saturation = 0.83f, lightness = 0.57f),
    primary60 = Color.hsl(hue = 65f, saturation = 0.83f, lightness = 0.57f, alpha = 0.6f),
    primary20 = Color.hsl(hue = 65f, saturation = 0.83f, lightness = 0.57f, alpha = 0.2f),

    body = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.05f),
    bodyMuted = Color.hsl(hue = 210f, saturation = 0f, lightness = 0.4f),

    accent = Color.hsl(hue = 194f, saturation = 0.86f, lightness = 0.48f),

    background = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.95f),
    backgroundDarker = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.9f),
    backgroundLighter = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 1f),

    surface = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.7f),

    black = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.05f),
    white = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.95f),

    error = Color.hsl(hue = 349f, saturation = 0.68f, lightness = 0.59f),
    error60 = Color.hsl(hue = 349f, saturation = 0.68f, lightness = 0.59f, alpha = 0.6f),
    error20 = Color.hsl(hue = 349f, saturation = 0.68f, lightness = 0.59f, alpha = 0.2f),
)

fun talkfrlyDarkThemeColors(): TalkfrlyColors = TalkfrlyColors(
    primary = Color.hsl(hue = 65f, saturation = 0.83f, lightness = 0.43f),
    primary60 = Color.hsl(hue = 65f, saturation = 0.83f, lightness = 0.57f, alpha = 0.6f),
    primary20 = Color.hsl(hue = 65f, saturation = 0.83f, lightness = 0.57f, alpha = 0.2f),

    body = Color.hsl(hue = 0f, saturation = 0.1f, lightness = 0.95f),
    bodyMuted = Color.hsl(hue = 0f, saturation = 0f, lightness = 0.6f),

    accent = Color.hsl(hue = 194f, saturation = 0.86f, lightness = 0.48f),

    background = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.05f),
    backgroundDarker = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0f),
    backgroundLighter = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.1f),

    surface = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.3f),

    black = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.05f),
    white = Color.hsl(hue = 210f, saturation = 0.1f, lightness = 0.95f),

    error = Color.hsl(hue = 349f, saturation = 0.68f, lightness = 0.59f),
    error60 = Color.hsl(hue = 349f, saturation = 0.68f, lightness = 0.59f, alpha = 0.6f),
    error20 = Color.hsl(hue = 349f, saturation = 0.68f, lightness = 0.59f, alpha = 0.2f),
)