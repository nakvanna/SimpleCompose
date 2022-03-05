package com.example.simplecompose.ui.widget

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.example.simplecompose.presentation.ScreenRoute

@Composable
fun OverflowMenu(
    navHostController: NavHostController,
    signOut: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
            signOut()
            expanded = false
        }) {
            Text("Logout", style = TextStyle(color = MaterialTheme.colors.error))
        }
    }
}