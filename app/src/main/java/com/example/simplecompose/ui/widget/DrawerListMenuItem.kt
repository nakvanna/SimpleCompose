package com.example.simplecompose.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simplecompose.domain.model.MenuItem

@Composable
fun DrawerListMenuItem(items: List<MenuItem>) {
    LazyColumn {
        items(items.size) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp,
                    )
                    .clip(
                        shape = RoundedCornerShape(4.dp),
                    )
                    .clickable {
                        items[index].onItemClick()
                    }
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = items[index].icon,
                    contentDescription = items[index].title,
                )
                Spacer(modifier = Modifier.width(32.dp))
                Column {
                    Text(
                        text = items[index].title,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}