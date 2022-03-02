package com.example.simplecompose.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.simplecompose.core.theme.CustomFontFamily
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OutlineButtonWithIcon(
    modifier: Modifier = Modifier,
    label: String,
    loadingLabel: String = label,
    @DrawableRes icon: Int,
    onClick: () -> Unit = {},
    isLoading: Boolean = false,
) {
    Surface(
        onClick = {
            if (!isLoading) onClick()
        },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Color.Gray),
        modifier = modifier.height(36.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(18.dp)
                    .padding(),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isLoading) loadingLabel.uppercase(Locale.getDefault()) else label.uppercase(
                    Locale.getDefault()
                ),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.button.fontSize,
                    fontFamily = CustomFontFamily.BattambangBold
                )
            )
            if (isLoading) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(width = 18.dp, height = 18.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}