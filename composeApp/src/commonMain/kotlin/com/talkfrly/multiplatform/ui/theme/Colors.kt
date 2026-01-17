package com.talkfrly.multiplatform.ui.theme

import androidx.compose.ui.graphics.Color

data class TalkfrlyColors(
    val primary: Color,
    val primary60: Color,
    val primary20: Color,

    val body: Color,
    val body80: Color,
    val body60: Color,

    val surface: Color,
    val surface80: Color,
    val surface30: Color,

    val error: Color,
    val error60: Color,
    val error20: Color,

    val background: Color,
)

fun talkfrlyLightThemeColors(): TalkfrlyColors = TalkfrlyColors(
    primary = Color(0xFFd3ec39),
    primary60 = Color(0x99d3ec39),
    primary20 = Color(0x33d3ec39),

    body = Color(0xFF181818),
    body80 = Color(0xCC181818),
    body60 = Color(0x99181818),

    surface = Color(0xFFe2e8f0),
    surface80 = Color(0xCCe2e8f0),
    surface30 = Color(0x4De2e8f0),

    error = Color(0xFFdb5375),
    error60 = Color(0x99db5375),
    error20 = Color(0x4Ddb5375),

    background = Color(0xFFffffff)
)

fun talkfrlyDarkThemeColors(): TalkfrlyColors = TalkfrlyColors(
    primary = Color(0xFFd3ec39),
    primary60 = Color(0x99d3ec39),
    primary20 = Color(0x33d3ec39),

    surface = Color(0xFF181818),
    surface80 = Color(0xCC181818),
    surface30 = Color(0x99181818),

    body = Color(0xFFe2e8f0),
    body80 = Color(0xCCe2e8f0),
    body60 = Color(0x4De2e8f0),

    error = Color(0xFFdb5375),
    error60 = Color(0x99db5375),
    error20 = Color(0x4Ddb5375),

    background = Color(0xFF000000)
)