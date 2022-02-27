package com.example.simplecompose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.presentation.about.AboutScreen
import com.example.simplecompose.presentation.auth.AuthScreen
import com.example.simplecompose.presentation.auth.phone.PhoneSignInScreen
import com.example.simplecompose.presentation.home.HomeScreen
import com.example.simplecompose.presentation.settings.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val isLogin: Boolean = auth.currentUser != null
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            val navHostController = rememberAnimatedNavController()
            Navigation(navHostController, isLogin)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navHostController: NavHostController,
    isLogin: Boolean,
) {
    AnimatedNavHost(
        startDestination = if (isLogin) ScreenRoute.HomeScreen.route else ScreenRoute.LoginScreen.route,
        navController = navHostController,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = 300)
            )
        },
        popEnterTransition = {
            slideInVertically(
                animationSpec = tween(durationMillis = 300)
            ) {
                -it
            } + fadeIn()
        }
    ) {
        composable(route = ScreenRoute.LoginScreen.route) {
            AuthScreen(navHostController)
        }
        composable(route = ScreenRoute.HomeScreen.route) {
            HomeScreen(navHostController)
        }
        composable(route = ScreenRoute.SettingsScreen.route) {
            SettingsScreen(navHostController)
        }
        composable(route = ScreenRoute.AboutScreen.route) {
            AboutScreen(navHostController)
        }
        composable(route = ScreenRoute.PhoneSignInFormScreen.route) {
            PhoneSignInScreen(navHostController)
        }
    }
}