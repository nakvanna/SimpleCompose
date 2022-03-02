package com.example.simplecompose.core.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.simplecompose.R

sealed class CustomFontFamily {
    companion object {
        val AngkorRegular = FontFamily(Font(R.font.angkor_regular))
        val BattambangRegular = FontFamily(Font(R.font.battambang_regular))
        val BattambangBold = FontFamily(Font(R.font.battambang_bold))
        val BayonRegular = FontFamily(Font(R.font.bayon_regular))
        val MetalRegular = FontFamily(Font(R.font.metal_regular))
    }
}