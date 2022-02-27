package com.example.simplecompose.presentation.auth

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.simplecompose.R
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.presentation.auth.google.GoogleSignInBtn
import com.example.simplecompose.ui.reusable.ImageBackground
import com.example.simplecompose.ui.reusable.PowerByCloudWare
import com.example.simplecompose.ui.theme.CustomFontFamily
import com.example.simplecompose.ui.theme.SimpleComposeTheme
import com.example.simplecompose.ui.widgets.CircleImage
import com.example.simplecompose.ui.widgets.OutlineButtonWithIcon
import com.example.simplecompose.util.CustomAnimate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navHostController: NavHostController) {
    val scope = rememberCoroutineScope()
    val config = LocalConfiguration.current

    var headerVisible by remember {
        mutableStateOf(false)
    }
    var btnPhoneVisible by remember {
        mutableStateOf(false)
    }
    var btnGoogleVisible by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(rememberScrollState()) {
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
                ImageBackground()
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
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                CircleImage(
                                    image = R.drawable.cloudware,
                                    modifier = Modifier.size(128.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.jc_by_cloudware),
                                    style = MaterialTheme.typography.h4.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = CustomFontFamily.AngkorRegular,
                                        background = MaterialTheme.colors.background
                                    )
                                )
                                Text(
                                    text = stringResource(R.string.login_with_any_account_below),
                                    style = MaterialTheme.typography.subtitle1.copy(
                                        fontFamily = CustomFontFamily.BayonRegular,
                                        background = MaterialTheme.colors.background
                                    )
                                )
                            }
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
                                isLoading = isLoading,
                                onClick = {
                                    scope.launch {
                                        isLoading = true
                                        delay(300)
                                        headerVisible = false
                                        btnPhoneVisible = false
                                        delay(100)
                                        btnGoogleVisible = false
                                        delay(100)
                                        isLoading = false
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






