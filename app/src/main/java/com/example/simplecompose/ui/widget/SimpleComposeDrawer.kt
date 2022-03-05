package com.example.simplecompose.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.simplecompose.R
import com.example.simplecompose.domain.model.MenuItem
import com.example.simplecompose.presentation.ScreenRoute
import kotlinx.coroutines.launch

@Composable
fun SimpleComposeDrawer(
    state: ScaffoldState,
    navHostController: NavHostController,
    displayName: String,
    providerRef: String,
) {
    val scope = rememberCoroutineScope()
    Box {
        Column {
            CircleImageRes(
                image = R.drawable.cloudware,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp
                )
            )
            Text(
                text = displayName,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 8.dp,
                )
            )
            Text(
                text = providerRef,
                style = MaterialTheme.typography.subtitle2,
                color = Color.Gray,
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 22.dp,
                )
            )
            Divider()
            DrawerListMenuItem(
                items = listOf(
                    MenuItem(
                        title = "About",
                        icon = Icons.Default.Info,
                        onItemClick = {
                            navHostController.navigate(ScreenRoute.AboutScreen.route)
                            scope.launch {
                                state.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    ),
                    MenuItem(
                        title = "Settings",
                        icon = Icons.Default.Settings,
                        onItemClick = {
                            navHostController.navigate(ScreenRoute.SettingsScreen.route)
                            scope.launch {
                                state.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    ),
                )
            )
        }
    }
}

