package com.example.simplecompose.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.ui.theme.SimpleComposeTheme
import com.example.simplecompose.ui.widgets.MyDrawer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navHostController: NavHostController) {
    SimpleComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                MyDrawer(
                    scope = scope,
                    state = scaffoldState,
                    navHostController = navHostController,
                )
            },
            topBar = {
                TopAppBar(
                    title = { Text(text = "Home") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        OverflowMenu(navHostController)
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = {
                        navHostController.navigate(ScreenRoute.SettingsScreen.route)
                    }
                ) {
                    Text(text = "Settings")
                }
            }
        }
    }
}

@Composable
fun OverflowMenu(navHostController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(onClick = {
            navHostController.navigate(ScreenRoute.AboutScreen.route)
        }) {
            Text("About")
        }
        DropdownMenuItem(onClick = {
            navHostController.navigate(ScreenRoute.SettingsScreen.route)
        }) {
            Text("Settings")
        }
        Divider()
        DropdownMenuItem(onClick = {
            auth.signOut()
            navHostController.navigate(ScreenRoute.LoginScreen.route) {
                popUpTo(ScreenRoute.LoginScreen.route) {
                    inclusive = true
                }
            }
        }) {
            Text("Logout", style = TextStyle(color = MaterialTheme.colors.error))
        }
    }
}
