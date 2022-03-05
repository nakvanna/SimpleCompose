package com.example.simplecompose.presentation.about

import SimpleComposeAppBar
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.simplecompose.core.theme.SimpleComposeTheme

@Composable
fun AboutScreen(navHostController: NavHostController) {
    Log.i("Init", "About init")
    SimpleComposeTheme {
        Scaffold(topBar = {
            SimpleComposeAppBar(
                title = "About",
                navHostController = navHostController
            )
        }) {
            Text(text = "Welcome about screen")
        }
    }
}