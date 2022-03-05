package com.example.simplecompose.presentation.auth

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.simplecompose.R
import com.example.simplecompose.core.theme.CustomAnimate
import com.example.simplecompose.core.theme.SimpleComposeTheme
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.presentation.auth.google.GoogleSignInBtn
import com.example.simplecompose.presentation.auth.widget.AuthHeader
import com.example.simplecompose.ui.reusable.ImageBackground
import com.example.simplecompose.ui.reusable.PowerByCloudWare
import com.example.simplecompose.ui.widget.OutlineButtonWithIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navHostController: NavHostController) {
    val scope = rememberCoroutineScope()
    val config = LocalConfiguration.current

    var headerVisible by remember { mutableStateOf(false) }
    var btnPhoneVisible by remember { mutableStateOf(false) }
    var btnGoogleVisible by remember { mutableStateOf(false) }
    var phoneSignInBtnLoading by remember { mutableStateOf(false) }

    //Init Animation
    LaunchedEffect(key1 = rememberScaffoldState()) {
        Log.i("AuthScreen", "Running")
        delay(100)
        headerVisible = true
        delay(100)
        btnPhoneVisible = true
        delay(100)
        btnGoogleVisible = true
    }

    SimpleComposeTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                ImageBackground() //Background image gif
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AnimatedVisibility(
                            visible = headerVisible,
                            enter = CustomAnimate.scaleIn,
                            exit = CustomAnimate.scaleOut,
                        ) {
                            AuthHeader(
                                title = stringResource(R.string.jc_by_cloudware),
                                caption = stringResource(R.string.login_with_any_account_below)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AnimatedVisibility(
                            visible = btnPhoneVisible,
                            enter = CustomAnimate.slideInLeft,
                            exit = CustomAnimate.slideOutRight
                        ) {
                            OutlineButtonWithIcon(
                                label = stringResource(R.string.sign_with_phone),
                                loadingLabel = stringResource(R.string.phone_signing),
                                icon = R.drawable.phone,
                                isLoading = phoneSignInBtnLoading,
                                onClick = {
                                    scope.launch {
                                        phoneSignInBtnLoading = true
                                        delay(300)
                                        headerVisible = false
                                        btnPhoneVisible = false
                                        delay(100)
                                        btnGoogleVisible = false
                                        delay(100)
                                        phoneSignInBtnLoading = false
                                        navHostController.navigate(ScreenRoute.PhoneSignInFormScreen.route)
                                    }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        AnimatedVisibility(
                            visible = btnGoogleVisible,
                            enter = CustomAnimate.slideInRight,
                            exit = CustomAnimate.slideOutLeft
                        ) {
                            GoogleSignInBtn(navHostController = navHostController)
                        }
                        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            Spacer(modifier = Modifier.height(64.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        PowerByCloudWare()
                    }
                }
            }
        }
    }
}






