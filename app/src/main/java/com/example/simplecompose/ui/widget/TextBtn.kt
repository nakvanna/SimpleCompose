package com.example.simplecompose.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplecompose.core.theme.CustomFontFamily

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextBtn(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .defaultMinSize(minWidth = 64.dp)
            .height(36.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.button.copy(
                    fontFamily = CustomFontFamily.BattambangBold,
                    color = MaterialTheme.colors.primary
                ),
            )
        }
    }
}