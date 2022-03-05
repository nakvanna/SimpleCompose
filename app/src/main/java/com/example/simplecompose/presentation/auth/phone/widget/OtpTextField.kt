package com.example.simplecompose.presentation.auth.phone.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.simplecompose.R

@Composable
fun OtpTextField(
    isError: (value: String) -> Boolean,
    validation: (value: String) -> String?,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    onChangeListener: (String) -> Unit
) {
    var otpIsError by remember { mutableStateOf(true) }
    var otpText by remember { mutableStateOf("") }
    var otpValidation: String? by remember { mutableStateOf(null) }

    Column {
        OutlinedTextField(
            value = otpText,
            onValueChange = {
                otpText = it
                otpIsError = isError(it)
                otpValidation = validation(it)
                onChangeListener(it)
            },
            maxLines = 1,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = onDone
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            shape = MaterialTheme.shapes.small,
            label = { Text(stringResource(R.string.opt_verify_code)) },
            trailingIcon = {
                if (otpIsError)
                    Icon(
                        Icons.Default.Warning,
                        "error",
                        tint = MaterialTheme.colors.error,
                    )
            },
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        )
        if (otpIsError) {
            Text(
                text = otpValidation ?: stringResource(R.string.require),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
    }
}