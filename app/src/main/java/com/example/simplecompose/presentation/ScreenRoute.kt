package com.example.simplecompose.presentation

sealed class ScreenRoute(val route: String) {
    object AuthScreen : ScreenRoute("auth_screen")
    object HomeScreen : ScreenRoute("home_screen")
    object SettingsScreen : ScreenRoute("settings_screen")
    object AboutScreen : ScreenRoute("about_screen")
    object PhoneSignInFormScreen : ScreenRoute("phone_sign_form_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
