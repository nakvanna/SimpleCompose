package com.example.simplecompose.presentation.auth.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simplecompose.R
import com.example.simplecompose.core.theme.CustomFontFamily
import com.example.simplecompose.ui.widget.CircleImageRes

@Composable
fun AuthHeader(
    title: String,
    caption: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CircleImageRes(
            image = R.drawable.cloudware,
            modifier = Modifier.size(112.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = TextStyle(
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily.AngkorRegular,
                background = MaterialTheme.colors.background
            ),
        )
        Text(
            text = caption,
            style = MaterialTheme.typography.subtitle1.copy(
                fontFamily = CustomFontFamily.BayonRegular,
                background = MaterialTheme.colors.background
            )
        )
    }
}