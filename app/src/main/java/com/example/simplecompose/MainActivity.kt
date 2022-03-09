package com.example.simplecompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.simplecompose.core.theme.CustomAnimate
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @Named("firebase_auth")
    lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("HardwareIds", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        val isLogin: Boolean = firebaseAuth.currentUser != null
        setContent {
            val navHostController = rememberAnimatedNavController()
            Navigation(
                navHostController = navHostController,
                isLogin = isLogin,
            )
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
        startDestination = if (isLogin) ScreenRoute.HomeScreen.route else ScreenRoute.AuthScreen.route,
        navController = navHostController,
        enterTransition = {
            CustomAnimate.slideInRight
        },
        popEnterTransition = {
            CustomAnimate.scaleIn
        }
    ) {
        composable(route = ScreenRoute.AuthScreen.route) {
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
