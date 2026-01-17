package com.talkfrly.multiplatform.ui.theme

import androidx.compose.ui.graphics.Color

data class TalkfrlyColors(
    val primary: Color,
    val primary60: Color,
    val primary20: Color,

    val body: Color,
    val body80: Color,
    val body60: Color,
    val body5: Color,

    val surface: Color,

    val error: Color,
    val error60: Color,
    val error20: Color,

    val background: Color,

    val black: Color,
    val white: Color,
)

fun talkfrlyLightThemeColors(): TalkfrlyColors = TalkfrlyColors(
    primary = Color(0xFFd3ec39),
    primary60 = Color(0x99d3ec39),
    primary20 = Color(0x33d3ec39),

    surface = Color(0xFFe2e8f0),

    body = Color(0xFF181818),
    body80 = Color(0xCC181818),
    body60 = Color(0x99181818),
    body5 = Color(0x0D181818),

    error = Color(0xFFdb5375),
    error60 = Color(0x99db5375),
    error20 = Color(0x4Ddb5375),

    background = Color(0xFFffffff),

    black = Color(0xFF181818),
    white = Color(0xFFe2e8f0)
)

fun talkfrlyDarkThemeColors(): TalkfrlyColors = TalkfrlyColors(
    primary = Color(0xFFd3ec39),
    primary60 = Color(0x99d3ec39),
    primary20 = Color(0x33d3ec39),

    surface = Color(0xFF181818),

    body = Color(0xFFe2e8f0),
    body80 = Color(0xCCe2e8f0),
    body60 = Color(0x99e2e8f0),
    body5 = Color(0x0De2e8f0),

    error = Color(0xFFdb5375),
    error60 = Color(0x99db5375),
    error20 = Color(0x4Ddb5375),

    background = Color(0xFF000000),

    black = Color(0xFF181818),
    white = Color(0xFFe2e8f0)
)