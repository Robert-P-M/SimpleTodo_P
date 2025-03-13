package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import at.robthered.simpletodo.features.core.presentation.UiText

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    onValueChange: (value: String) -> Unit,
    error: UiText? = null,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    placeholder: String? = null,
    label: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = if(singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        enabled = enabled,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = textStyle,
        colors = colors,
        minLines = minLines,
        maxLines = maxLines,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        suffix = suffix,
        prefix = prefix,
        label = {
            Text(
                text = label
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        placeholder = {
            placeholder?.let {
                Text(
                    text = placeholder
                )
            }
        },
        supportingText = {
            error?.let {
                Text(
                    text = it.asString(),
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                )
            }
        }
    )
}