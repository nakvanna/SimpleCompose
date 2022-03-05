package com.example.simplecompose.presentation.home.widget

import android.text.Html
import android.text.TextUtils
import android.util.TypedValue
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.simplecompose.R
import com.example.simplecompose.core.theme.CustomFontFamily

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListItemCard(
    image: String,
    title: String,
    content: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                onClick()
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp),
            elevation = 2.dp,
        ) {
            Row(
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 16.dp,
                )
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = image,
                        builder = {
                            transformations(RoundedCornersTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(width = 100.dp, height = 56.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = CustomFontFamily.BattambangRegular
                        )
                    )
                    //Integrate TextView from XML
                    AndroidView(
                        factory = {
                            TextView(it)
                        }
                    ) { textView ->
                        textView.apply {
                            text = Html.fromHtml(content)
                            maxLines = 2
                            ellipsize = TextUtils.TruncateAt.END
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.gray
                                )
                            )
                            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        }

                    }
                }
            }
        }
    }
}