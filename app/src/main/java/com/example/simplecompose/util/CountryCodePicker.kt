package com.example.simplecompose.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.simplecompose.R
import com.example.simplecompose.domain.model.CountryCodeItem
import com.example.simplecompose.ui.theme.CustomFontFamily
import com.example.simplecompose.ui.widgets.TextBtn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryCodePicker(
    onClick: (String) -> Unit
) {
    val options: List<CountryCodeItem> = listOf(
        CountryCodeItem(
            flag = "\uD83C\uDDF0\uD83C\uDDED",
            dialCode = "+855",
            fullName = "Cambodia",
            shortName = "kh"
        ),
        CountryCodeItem(
            flag = "\uD83C\uDDF9\uD83C\uDDED",
            dialCode = "+66",
            fullName = "Thai",
            shortName = "th"
        ),
        CountryCodeItem(
            flag = "\uD83C\uDDFB\uD83C\uDDF3",
            dialCode = "+84",
            fullName = "Vietnam",
            shortName = "vn"
        ),
        CountryCodeItem(
            flag = "\uD83C\uDDF2\uD83C\uDDF2",
            dialCode = "+95",
            fullName = "Myanmar",
            shortName = "mm"
        ),
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
    var openDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .border(
                border = BorderStroke(1.dp, color = Color.Gray),
                shape = RoundedCornerShape(4.dp)
            ),
        onClick = {
            openDialog = true
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                horizontal = 16.dp, vertical = 16.dp
            )
        ) {
            Row {
                Text(text = selectedOption.flag)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = selectedOption.shortName.uppercase(Locale.getDefault()))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = selectedOption.dialCode)
            }
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
        }
        if (openDialog) {
            Dialog(
                onDismissRequest = {
                    openDialog = false
                }) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.fillMaxHeight(.8f)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .height(64.dp)
                                .padding(
                                    top = 24.dp,
                                    start = 24.dp,
                                    bottom = 8.dp
                                )
                        ) {
                            Text(
                                text = stringResource(R.string.select_the_country),
                                style = MaterialTheme.typography.body1.copy(
                                    fontFamily = CustomFontFamily.BattambangRegular,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Divider()
                        Column(
                            Modifier
                                .selectableGroup()
                                .padding(bottom = 52.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            options.forEach { option ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .selectable(
                                            selected = (option == selectedOption),
                                            onClick = {
                                                scope.launch {
                                                    onOptionSelected(option)
                                                    onClick(option.dialCode)
                                                    delay(200)
                                                    openDialog = false
                                                }
                                            },
                                            role = Role.RadioButton
                                        )
                                        .padding(horizontal = 24.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (option == selectedOption),
                                        onClick = null, // null recommended for accessibility with screen readers
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${option.flag} ${option.fullName} (${
                                                option.shortName.uppercase(
                                                    Locale.getDefault()
                                                )
                                            })",
                                            style = MaterialTheme.typography.body1.merge(),
                                        )
                                        Text(
                                            text = option.dialCode,
                                            style = MaterialTheme.typography.body1.merge(),
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Column(
                            modifier = Modifier.height(52.dp)
                        ) {
                            Divider()
                            Row(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextBtn(
                                    label = stringResource(id = R.string.ok),
                                    onClick = { openDialog = false }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}