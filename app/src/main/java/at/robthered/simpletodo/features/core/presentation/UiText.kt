package at.robthered.simpletodo.features.core.presentation

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class StringResourceId(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf(),
    ) : UiText

    class PluralResourceId(
        @PluralsRes val id: Int,
        val count: Int,
        val args: Array<Any> = arrayOf(),
    ) : UiText

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceId -> context.resources.getString(id, *args)
            is PluralResourceId -> context.resources.getQuantityString(id, count, *args)
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(id = id, *args)
            is PluralResourceId -> pluralStringResource(id, count, *args)
        }
    }
}

@Composable
fun UiText.asAnnotatedString(color: Color = MaterialTheme.colorScheme.primary): AnnotatedString {
    return when (this) {
        is UiText.DynamicString -> AnnotatedString(value)
        is UiText.StringResourceId -> {
            val formattedText = stringResource(id = id, *args)
            buildAnnotatedString {
                appendFormattedText(formattedText, args, color)
            }
        }
        is UiText.PluralResourceId -> {
            val formattedText = pluralStringResource(id, count, *args)
            buildAnnotatedString {
                appendFormattedText(formattedText, args, color)
            }
        }
    }
}

private fun AnnotatedString.Builder.appendFormattedText(
    formattedText: String,
    args: Array<Any>,
    color: Color
) {
    var lastIndex = 0
    args.forEach { arg ->
        val placeholder = arg.toString()
        val startIndex = formattedText.indexOf(placeholder, lastIndex)
        if (startIndex != -1) {
            append(formattedText.substring(lastIndex, startIndex))
            withStyle(style = SpanStyle(color = color, fontWeight = FontWeight.Bold)) {
                append(placeholder)
            }
            lastIndex = startIndex + placeholder.length
        }
    }
    append(formattedText.substring(lastIndex))
}