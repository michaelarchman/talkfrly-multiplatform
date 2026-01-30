package com.talkfrly.multiplatform.domain.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

fun parseMarkdownToAnnotatedString(
    text: String,
    linkColor: Color = Color.Blue,
): AnnotatedString = buildAnnotatedString {
    var currentIndex = 0

    // Pattern for markdown elements
    val patterns = listOf(
        // Bold: **text** or __text__
        Triple(Regex("""(?<!\*)\*{2}(.+?)\*{2}(?!\*)"""), "bold", null),
        // Italic: *text* or _text_
        Triple(Regex("""(?<!\*)\*(.+?)\*(?!\*)"""), "italic", null),
        Triple(Regex("""_(.+?)_"""), "italic", null),
        // Headers: # text, ## text, etc.
        Triple(Regex("""^#{1,6}\s+(.+?)$""", RegexOption.MULTILINE), "header", null),
        // Links: [text](url)
        Triple(Regex("""\[(.+?)]\((.+?)\)"""), "link", null),
    )

    var processedText = text
    val replacements = mutableListOf<Triple<String, Int, String>>()

    // Process bold
    Regex("""(?<!\*)\*{2}(.+?)\*{2}(?!\*)""").findAll(text).forEach { match ->
        replacements.add(Triple(match.value, match.range.first, "bold:${match.groupValues[1]}"))
    }

    // Process italic
    Regex("""(?<!\*)\*(.+?)\*(?!\*)""").findAll(text).forEach { match ->
        replacements.add(Triple(match.value, match.range.first, "italic:${match.groupValues[1]}"))
    }

    // Process headers
    Regex("""^#{1,6}\s+(.+?)$""", RegexOption.MULTILINE).findAll(text).forEach { match ->
        replacements.add(Triple(match.value, match.range.first, "header:${match.groupValues[1]}"))
    }

    // Process links
    Regex("""\[(.+?)]\((.+?)\)""").findAll(text).forEach { match ->
        replacements.add(Triple(match.value, match.range.first, "link:${match.groupValues[1]}:${match.groupValues[2]}"))
    }

    // Sort by position (descending) to replace from end to start
    replacements.sortByDescending { it.second }

    var result = text
    replacements.forEach { (original, _, replacement) ->
        result = result.replaceFirst(original, replacement)
    }

    // Now build the annotated string
    var i = 0
    while (i < result.length) {
        when {
            result.substring(i).startsWith("bold:") -> {
                val endIndex = result.indexOf(":", i + 5)
                if (endIndex != -1) {
                    val content = result.substring(i + 5, endIndex)
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append(content)
                    pop()
                    i = endIndex + 1
                } else {
                    append(result[i])
                    i++
                }
            }
            result.substring(i).startsWith("italic:") -> {
                val endIndex = result.indexOf(":", i + 7)
                if (endIndex != -1) {
                    val content = result.substring(i + 7, endIndex)
                    pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    append(content)
                    pop()
                    i = endIndex + 1
                } else {
                    append(result[i])
                    i++
                }
            }
            result.substring(i).startsWith("header:") -> {
                val endIndex = result.indexOf(":", i + 7)
                if (endIndex != -1) {
                    val content = result.substring(i + 7, endIndex)
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = androidx.compose.ui.unit.TextUnit(20f, androidx.compose.ui.unit.TextUnitType.Sp)))
                    append(content)
                    pop()
                    i = endIndex + 1
                } else {
                    append(result[i])
                    i++
                }
            }
            result.substring(i).startsWith("link:") -> {
                val parts = result.substring(i + 5).split(":")
                if (parts.size >= 2) {
                    val linkText = parts[0]
                    val linkUrl = parts[1]
                    val linkEndIndex = i + 5 + linkText.length + 1 + linkUrl.length
                    pushStyle(SpanStyle(color = linkColor))
                    append(linkText)
                    addStringAnnotation("URL", linkUrl, length - linkText.length, length)
                    pop()
                    i = linkEndIndex
                } else {
                    append(result[i])
                    i++
                }
            }
            else -> {
                append(result[i])
                i++
            }
        }
    }
}

fun parseMarkdownSimple(text: String, linkColor: Color = Color.Blue): AnnotatedString = buildAnnotatedString {
    var index = 0

    while (index < text.length) {
        // Check for bold **text**
        if (index < text.length - 3 && text.substring(index).startsWith("**")) {
            val endIndex = text.indexOf("**", index + 2)
            if (endIndex != -1) {
                val boldText = text.substring(index + 2, endIndex)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append(boldText)
                pop()
                index = endIndex + 2
                continue
            }
        }

        // Check for italic *text*
        if (text[index] == '*' && index > 0 && index < text.length - 1) {
            val nextIndex = text.indexOf("*", index + 1)
            if (nextIndex != -1 && text.getOrNull(index - 1) != '*' && text.getOrNull(nextIndex + 1) != '*') {
                val italicText = text.substring(index + 1, nextIndex)
                pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                append(italicText)
                pop()
                index = nextIndex + 1
                continue
            }
        }

        // Check for links [text](url)
        if (text[index] == '[') {
            val closeIndex = text.indexOf("]", index)
            if (closeIndex != -1 && closeIndex + 1 < text.length && text[closeIndex + 1] == '(') {
                val urlEnd = text.indexOf(")", closeIndex + 2)
                if (urlEnd != -1) {
                    val linkText = text.substring(index + 1, closeIndex)
                    val url = text.substring(closeIndex + 2, urlEnd)
                    pushStyle(SpanStyle(color = linkColor))
                    append(linkText)
                    addStringAnnotation("URL", url, length - linkText.length, length)
                    pop()
                    index = urlEnd + 1
                    continue
                }
            }
        }

        append(text[index])
        index++
    }
}
