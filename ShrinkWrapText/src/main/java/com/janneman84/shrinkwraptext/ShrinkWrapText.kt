package com.janneman84.shrinkwraptext

import android.util.Log
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import kotlin.math.ceil

@Composable
private fun ShrinkWrapText(
    modifier: Modifier = Modifier,
    text: String? = null,
    annotatedText: AnnotatedString? = null,
    color: Color = Color.Unspecified,
    colorProducer: ColorProducer? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent>? = null,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    shrinkWrap: Boolean,
    basic: Boolean = false
) {
    var maxLineWidth = -1f
    var shrinkWrap = shrinkWrap

    val layoutModifier = modifier.layout({ measurable, constraints ->
        shrinkWrap = shrinkWrap && constraints.maxWidth > constraints.minWidth
        val placeable1 = measurable.measure(constraints)

        if (!shrinkWrap || maxLineWidth == -1f) {
            layout(placeable1.width, placeable1.height) {
                placeable1.placeRelative(0, 0)
            }
        } else {
            val placeable2 = measurable.measure(
                Constraints(
                    minWidth = constraints.minWidth,
                    maxWidth = ceil(maxLineWidth).toInt(),
                    minHeight = constraints.minHeight,
                    maxHeight = constraints.maxHeight
                )
            )
            layout(ceil(maxLineWidth).toInt(), placeable2.height) {
                placeable2.placeRelative(0, 0)
            }
        }
    })

    val textLayout: (TextLayoutResult) -> Unit = {
        if (shrinkWrap && maxLineWidth == -1f && it.lineCount > 1) {
            for (i in 0 until it.lineCount) {
                val width = it.getLineRight(i) - it.getLineLeft(i)
                if (width > maxLineWidth) {
                    maxLineWidth = width
                }
            }
        }
        onTextLayout?.invoke(it)
    }

    if (basic) {
        if (text != null) {
            BasicText(
                text = text, modifier = layoutModifier, style = style, onTextLayout = textLayout, overflow = overflow,
                softWrap = softWrap, maxLines = maxLines, minLines = minLines, color = colorProducer
            )
        } else {
            BasicText(
                text = annotatedText!!, modifier = layoutModifier, style = style, onTextLayout = textLayout, overflow = overflow,
                softWrap = softWrap, maxLines = maxLines, minLines = minLines, inlineContent = inlineContent ?: mapOf(), color = colorProducer
            )
        }
    } else {
        if (text != null) {
            Text(
                text = text, modifier = layoutModifier,
                color = color, fontSize = fontSize, fontStyle = fontStyle, fontWeight = fontWeight, fontFamily = fontFamily,
                letterSpacing = letterSpacing, textDecoration = textDecoration, textAlign = textAlign,
                lineHeight = lineHeight, overflow = overflow, softWrap = softWrap, maxLines = maxLines, minLines = minLines,
                onTextLayout = textLayout, style = style
            )
        }
        else {
            Text(
                annotatedText!!, modifier = layoutModifier,
                color = color, fontSize = fontSize, fontStyle = fontStyle, fontWeight = fontWeight, fontFamily = fontFamily,
                letterSpacing = letterSpacing, textDecoration = textDecoration, textAlign = textAlign,
                lineHeight = lineHeight, overflow = overflow, softWrap = softWrap, maxLines = maxLines, minLines = minLines,
                inlineContent = inlineContent ?: mapOf(), onTextLayout = textLayout, style = style
            )
        }
    }
}

/**
 * High level element that displays text and provides semantics / accessibility information.
 *
 * The default [style] uses the [androidx.compose.material3.LocalTextStyle] provided by the [androidx.compose.material3.MaterialTheme] / components. If
 * you are setting your own style, you may want to consider first retrieving [androidx.compose.material3.LocalTextStyle],
 * and using [androidx.compose.ui.text.TextStyle.copy] to keep any theme defined attributes, only modifying the specific
 * attributes you want to override.
 *
 * For ease of use, commonly used parameters from [androidx.compose.ui.text.TextStyle] are also present here. The order of
 * precedence is as follows:
 * - If a parameter is explicitly set here (i.e, it is _not_ `null` or [androidx.compose.ui.unit.TextUnit.Companion.Unspecified]),
 * then this parameter will always be used.
 * - If a parameter is _not_ set, (`null` or [androidx.compose.ui.unit.TextUnit.Companion.Unspecified]), then the corresponding value
 * from [style] will be used instead.
 *
 * Additionally, for [color], if [color] is not set, and [style] does not have a color, then
 * [androidx.compose.material3.LocalContentColor] will be used.
 *
 * @param text the text to be displayed
 * @param modifier the [androidx.compose.ui.Modifier] to be applied to this layout node
 * @param color [androidx.compose.ui.graphics.Color] to apply to the text. If [androidx.compose.ui.graphics.Color.Companion.Unspecified], and [style] has no color set,
 * this will be [androidx.compose.material3.LocalContentColor].
 * @param fontSize the size of glyphs to use when painting the text. See [androidx.compose.ui.text.TextStyle.fontSize].
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic).
 * See [androidx.compose.ui.text.TextStyle.fontStyle].
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [androidx.compose.ui.text.font.FontWeight.Companion.Bold]).
 * @param fontFamily the font family to be used when rendering the text. See [androidx.compose.ui.text.TextStyle.fontFamily].
 * @param letterSpacing the amount of space to add between each letter.
 * See [androidx.compose.ui.text.TextStyle.letterSpacing].
 * @param textDecoration the decorations to paint on the text (e.g., an underline).
 * See [androidx.compose.ui.text.TextStyle.textDecoration].
 * @param textAlign the alignment of the text within the lines of the paragraph.
 * See [androidx.compose.ui.text.TextStyle.textAlign].
 * @param lineHeight line height for the [androidx.compose.ui.text.Paragraph] in [androidx.compose.ui.unit.TextUnit] unit, e.g. SP or EM.
 * See [androidx.compose.ui.text.TextStyle.lineHeight].
 * @param overflow how visual overflow should be handled.
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary. If the text exceeds the given number of lines, it will be truncated according to
 * [overflow] and [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines].
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 * [androidx.compose.ui.text.TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw selection around the text.
 * @param style style configuration for the text such as color, font, line height etc.
 * @param shrinkWrap shrinks the width to tightly fit the text, even when multi-lined. Recommended for chat bubbles.
 */

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    shrinkWrap: Boolean
) {
    Log.d("jojo", "Text shrinkwrap: $shrinkWrap")
    ShrinkWrapText(
        modifier, text, null,  color, null, fontSize, fontStyle, fontWeight, fontFamily, letterSpacing, textDecoration,
        textAlign, lineHeight, overflow, softWrap, maxLines, minLines, mapOf(), onTextLayout, style, shrinkWrap
    )
}

@Deprecated(
    "Maintained for binary compatibility. Use version with minLines instead",
    level = DeprecationLevel.HIDDEN
)
@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    shrinkWrap: Boolean
) {
    Text(
        text,
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        1,
        onTextLayout,
        style,
        shrinkWrap
    )
}

/**
 * High level element that displays text and provides semantics / accessibility information.
 *
 * The default [style] uses the [androidx.compose.material3.LocalTextStyle] provided by the [androidx.compose.material3.MaterialTheme] / components. If
 * you are setting your own style, you may want to consider first retrieving [androidx.compose.material3.LocalTextStyle],
 * and using [androidx.compose.ui.text.TextStyle.copy] to keep any theme defined attributes, only modifying the specific
 * attributes you want to override.
 *
 * For ease of use, commonly used parameters from [androidx.compose.ui.text.TextStyle] are also present here. The order of
 * precedence is as follows:
 * - If a parameter is explicitly set here (i.e, it is _not_ `null` or [androidx.compose.ui.unit.TextUnit.Companion.Unspecified]),
 * then this parameter will always be used.
 * - If a parameter is _not_ set, (`null` or [androidx.compose.ui.unit.TextUnit.Companion.Unspecified]), then the corresponding value
 * from [style] will be used instead.
 *
 * Additionally, for [color], if [color] is not set, and [style] does not have a color, then
 * [androidx.compose.material3.LocalContentColor] will be used.
 *
 * @param text the text to be displayed
 * @param modifier the [androidx.compose.ui.Modifier] to be applied to this layout node
 * @param color [androidx.compose.ui.graphics.Color] to apply to the text. If [androidx.compose.ui.graphics.Color.Companion.Unspecified], and [style] has no color set,
 * this will be [androidx.compose.material3.LocalContentColor].
 * @param fontSize the size of glyphs to use when painting the text. See [androidx.compose.ui.text.TextStyle.fontSize].
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic).
 * See [androidx.compose.ui.text.TextStyle.fontStyle].
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [androidx.compose.ui.text.font.FontWeight.Companion.Bold]).
 * @param fontFamily the font family to be used when rendering the text. See [androidx.compose.ui.text.TextStyle.fontFamily].
 * @param letterSpacing the amount of space to add between each letter.
 * See [androidx.compose.ui.text.TextStyle.letterSpacing].
 * @param textDecoration the decorations to paint on the text (e.g., an underline).
 * See [androidx.compose.ui.text.TextStyle.textDecoration].
 * @param textAlign the alignment of the text within the lines of the paragraph.
 * See [androidx.compose.ui.text.TextStyle.textAlign].
 * @param lineHeight line height for the [androidx.compose.ui.text.Paragraph] in [androidx.compose.ui.unit.TextUnit] unit, e.g. SP or EM.
 * See [androidx.compose.ui.text.TextStyle.lineHeight].
 * @param overflow how visual overflow should be handled.
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary. If the text exceeds the given number of lines, it will be truncated according to
 * [overflow] and [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines].
 * @param inlineContent a map storing composables that replaces certain ranges of the text, used to
 * insert composables into text layout. See [androidx.compose.foundation.text.InlineTextContent].
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 * [androidx.compose.ui.text.TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw selection around the text.
 * @param style style configuration for the text such as color, font, line height etc.
 * @param shrinkWrap shrinks the width to tightly fit the text, even when multi-lined. Recommended for chat bubbles.
 */
@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    shrinkWrap: Boolean
) {
    ShrinkWrapText(
        modifier, null, text,  color, null, fontSize, fontStyle, fontWeight, fontFamily, letterSpacing, textDecoration,
        textAlign, lineHeight, overflow, softWrap, maxLines, minLines, inlineContent, onTextLayout, style, shrinkWrap
    )
}

@Deprecated(
    "Maintained for binary compatibility. Use version with minLines instead",
    level = DeprecationLevel.HIDDEN
)
@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    shrinkWrap: Boolean
) {
    Text(
        text,
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        1,
        inlineContent,
        onTextLayout,
        style,
        shrinkWrap
    )
}

/**
 * Basic element that displays text and provides semantics / accessibility information.
 * Typically you will instead want to use [androidx.compose.material.Text], which is
 * a higher level Text element that contains semantics and consumes style information from a theme.
 *
 * @param text The text to be displayed.
 * @param modifier [androidx.compose.ui.Modifier] to apply to this layout node.
 * @param style Style configuration for the text such as color, font, line height etc.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [androidx.compose.ui.text.TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw selection around the text.
 * @param overflow How visual overflow should be handled.
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary. If the text exceeds the given number of lines, it will be truncated according to
 * [overflow] and [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines].
 * @param color Overrides the text color provided in [style]
 * @param shrinkWrap shrinks the width to tightly fit the text, even when multi-lined. Recommended for chat bubbles.
 */
@Composable
fun BasicText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    color: ColorProducer? = null,
    shrinkWrap: Boolean,
) {
    ShrinkWrapText(
        text = text,
        modifier = modifier,
        style = style,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        colorProducer = color,
        shrinkWrap = shrinkWrap,
        basic = true
    )
}

/**
 * Basic element that displays text and provides semantics / accessibility information.
 * Typically you will instead want to use [androidx.compose.material.Text], which is
 * a higher level Text element that contains semantics and consumes style information from a theme.
 *
 * @param text The text to be displayed.
 * @param modifier [androidx.compose.ui.Modifier] to apply to this layout node.
 * @param style Style configuration for the text such as color, font, line height etc.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [androidx.compose.ui.text.TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw selection around the text.
 * @param overflow How visual overflow should be handled.
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary. If the text exceeds the given number of lines, it will be truncated according to
 * [overflow] and [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines].
 * @param inlineContent A map store composables that replaces certain ranges of the text. It's
 * used to insert composables into text layout. Check [androidx.compose.foundation.text.InlineTextContent] for more information.
 * @param color Overrides the text color provided in [style]
 * @param shrinkWrap shrinks the width to tightly fit the text, even when multi-lined. Recommended for chat bubbles.
 */
@Composable
fun BasicText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    color: ColorProducer? = null,
    shrinkWrap: Boolean
) {
    ShrinkWrapText(
        annotatedText = text,
        modifier = modifier,
        style = style,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        colorProducer = color,
        shrinkWrap = shrinkWrap,
        basic = true
    )
}

@Deprecated("Maintained for binary compatibility", level = DeprecationLevel.HIDDEN)
@Composable
fun BasicText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    shrinkWrap: Boolean
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        minLines = 1,
        maxLines = maxLines,
        inlineContent = inlineContent,
        shrinkWrap = shrinkWrap
    )
}

@Deprecated("Maintained for binary compat", level = DeprecationLevel.HIDDEN)
@Composable
fun BasicText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    shrinkWrap: Boolean
) = BasicText(text, modifier, style, onTextLayout, overflow, softWrap, maxLines, minLines, shrinkWrap = shrinkWrap)

@Deprecated("Maintained for binary compat", level = DeprecationLevel.HIDDEN)
@Composable
fun BasicText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    shrinkWrap: Boolean
) = BasicText(
    text = text,
    modifier = modifier,
    style = style,
    onTextLayout = onTextLayout,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    minLines = minLines,
    inlineContent = inlineContent,
    shrinkWrap = shrinkWrap
)