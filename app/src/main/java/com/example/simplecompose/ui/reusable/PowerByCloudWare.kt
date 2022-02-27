package com.example.simplecompose.ui.reusable

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.simplecompose.R
import com.example.simplecompose.ui.theme.CustomFontFamily

@Composable
fun PowerByCloudWare() {
    Text(
        text = stringResource(R.string.power_by_cloudware),
        style = TextStyle(
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            fontFamily = CustomFontFamily.MetalRegular
        ),
    )
}