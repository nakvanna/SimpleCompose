package com.example.simplecompose.presentation.auth.phone.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.simplecompose.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneNumberTextField(
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    isError: (value: String) -> Boolean,
    validation: (value: String) -> String?,
    onChangeListener: (String) -> Unit
) {
    var countyCodeSelect by remember { mutableStateOf("+855") }
    var phoneNumberIsError by remember { mutableStateOf(true) }
    var phoneNumberText by remember { mutableStateOf("") }
    var phoneNumberValidation: String? by remember { mutableStateOf(null) }

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            CountryCodePicker {
                countyCodeSelect = it
                onChangeListener("$countyCodeSelect $phoneNumberText")
            }
            Spacer(modifier = Modifier.width(4.dp))
            OutlinedTextField(
                value = phoneNumberText,
                onValueChange = {
                    phoneNumberText = it
                    phoneNumberIsError = isError(it)
                    phoneNumberValidation = validation(it)
                    onChangeListener("$countyCodeSelect $phoneNumberText")
                },
                shape = MaterialTheme.shapes.small,
                label = { Text(stringResource(R.string.phone_number)) },
                singleLine = true,
                isError = phoneNumberIsError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = onDone
                ),
                trailingIcon = {
                    if (phoneNumberIsError)
                        Icon(
                            Icons.Default.Warning,
                            "error",
                            tint = MaterialTheme.colors.error,
                        )
                },
                modifier = Modifier.background(color = MaterialTheme.colors.background)
            )
        }
        if (phoneNumberIsError) {
            Text(
                text = phoneNumberValidation
                    ?: stringResource(R.string.require),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 150.dp)
            )
        }
    }
}