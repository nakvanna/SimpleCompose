package com.example.simplecompose.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val icon: ImageVector,
    val title: String,
    val onItemClick: () -> Unit
)