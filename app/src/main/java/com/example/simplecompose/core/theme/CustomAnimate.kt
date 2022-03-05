package com.example.simplecompose.core.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment

sealed class CustomAnimate {
    companion object {
        // Scale In / Out
        @OptIn(ExperimentalAnimationApi::class)
        val scaleIn = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically)

        @OptIn(ExperimentalAnimationApi::class)
        val scaleOut = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)

        // Slide In left / Out Right
        val slideInLeft = slideInHorizontally(
            initialOffsetX = { -it * 2 },
            animationSpec = tween(durationMillis = 200)
        )
        val slideOutRight = slideOutHorizontally(
            targetOffsetX = { it * 2 },
            animationSpec = tween(durationMillis = 200)
        )

        // Slide In Right / Out Left
        val slideInRight = slideInHorizontally(
            initialOffsetX = { it * 2 },
            animationSpec = tween(durationMillis = 200)
        )
        val slideOutLeft = slideOutHorizontally(
            targetOffsetX = { -it * 2 },
            animationSpec = tween(durationMillis = 200)
        )
    }
}
