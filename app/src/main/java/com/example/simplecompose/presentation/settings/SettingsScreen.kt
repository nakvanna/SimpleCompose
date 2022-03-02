package com.example.simplecompose.presentation.settings

import SimpleComposeAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.simplecompose.core.theme.SimpleComposeTheme

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    SimpleComposeTheme {
        Scaffold(
            topBar = {
                SimpleComposeAppBar(
                    title = "Settings",
                    navHostController = navHostController
                )
            }
        ) {
            Text(text = "Welcome settings screen")
        }
    }
}