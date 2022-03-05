package com.example.simplecompose.presentation.auth.phone

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.simplecompose.R
import com.example.simplecompose.core.theme.CustomAnimate
import com.example.simplecompose.core.theme.CustomFontFamily
import com.example.simplecompose.core.theme.SimpleComposeTheme
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.presentation.auth.phone.widget.OtpTextField
import com.example.simplecompose.presentation.auth.phone.widget.PhoneNumberTextField
import com.example.simplecompose.presentation.auth.widget.AuthHeader
import com.example.simplecompose.ui.reusable.ImageBackground
import com.example.simplecompose.ui.reusable.PowerByCloudWare
import com.example.simplecompose.ui.widget.OutlineButtonWithIcon
import com.example.simplecompose.ui.widget.TextBtn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun PhoneSignInScreen(
    navHostController: NavHostController,
    viewModel: PhoneSignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val activity = LocalContext.current as Activity
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    var signLoading by remember { mutableStateOf(false) }
    var headerVisible by remember { mutableStateOf(false) }
    var btnVerifyVisible by remember { mutableStateOf(false) }

    //Phone number outline text field
    var phoneNumberTextVisible by remember { mutableStateOf(false) }
    var fullPhoneNumber by remember { mutableStateOf("+855 ") }

    //Otp code outline text field
    var otpTextVisible by remember { mutableStateOf(false) }
    var otpText by remember { mutableStateOf("") }

    //Phone number confirm to send code
    var confirmDialog by remember { mutableStateOf(false) }

    var isSendVerifyBtn by remember { mutableStateOf(true) }

    //Countdown timer
    var timeOut by remember { mutableStateOf(60 * 1000) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(60) }

    LaunchedEffect(key1 = timeOut, key2 = isTimerRunning) {
        if (timeOut > 0 && isTimerRunning) {
            delay(1000)
            timeOut -= 1000
            timeLeft = timeOut / 1000
        }
    }

    //Real device auto sign in without verify otp code
    viewModel.autoSignInCredentialSuccess = {
        signLoading = false
        navHostController.navigate(
            ScreenRoute.HomeScreen.route
        ) {
            popUpTo(ScreenRoute.HomeScreen.route) {
                inclusive = true
            }
        }
    }
    viewModel.autoSignInCredentialFailure = {
        Log.i("Phone", "Auto sign in with credential failure")
    }

    //Init Animation
    LaunchedEffect(key1 = rememberScaffoldState()) {
        delay(100)
        headerVisible = true
        delay(100)
        phoneNumberTextVisible = true
        delay(100)
        btnVerifyVisible = true
    }

    //Override Back Button
    BackHandler {
        scope.launch {
            delay(100)
            headerVisible = false
            delay(100)
            phoneNumberTextVisible = false
            delay(100)
            btnVerifyVisible = false
            navHostController.popBackStack()
        }
    }

    SimpleComposeTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                ImageBackground() //Background image gif
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    AnimatedVisibility(
                        visible = headerVisible,
                        enter = CustomAnimate.scaleIn,
                        exit = CustomAnimate.scaleOut
                    ) {
                        AuthHeader(
                            title = stringResource(R.string.sign_in_with_phone),
                            caption = stringResource(R.string.we_happy_to_see_you_here)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AnimatedVisibility(
                            visible = phoneNumberTextVisible,
                            enter = CustomAnimate.slideInLeft,
                            exit = CustomAnimate.slideOutRight,
                        ) {
                            PhoneNumberTextField(
                                isError = {
                                    !viewModel.phoneInputValidation(context, it)
                                        .isNullOrEmpty()
                                },
                                validation = {
                                    viewModel.phoneInputValidation(context, it)
                                },
                                onChangeListener = {
                                    fullPhoneNumber = it
                                },
                                onDone = {
                                    keyboardController?.hide()
                                }
                            )
                        }

                        AnimatedVisibility(
                            visible = otpTextVisible,
                            enter = CustomAnimate.slideInLeft,
                            exit = CustomAnimate.slideOutRight
                        ) {
                            OtpTextField(
                                isError = {
                                    !viewModel.otpInputValidation(context, it)
                                        .isNullOrEmpty()
                                },
                                validation = {
                                    viewModel.otpInputValidation(context, it)
                                },
                                onChangeListener = {
                                    otpText = it
                                },
                                onDone = {
                                    keyboardController?.hide()
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        AnimatedVisibility(
                            visible = btnVerifyVisible,
                            enter = CustomAnimate.slideInRight,
                            exit = CustomAnimate.slideOutRight,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                OutlineButtonWithIcon(
                                    label =
                                    if (isSendVerifyBtn) stringResource(R.string.send_verify_code)
                                    else stringResource(R.string.opt_verify_code),
                                    icon = R.drawable.phone,
                                    isLoading = signLoading,
                                    onClick = {
                                        if (isSendVerifyBtn) {
                                            keyboardController?.hide()
                                            confirmDialog = true
                                            signLoading = true
                                        } else {
                                            if (viewModel.otpInputValidation(context, otpText)
                                                    .isNullOrEmpty()
                                            ) {
                                                signLoading = true
                                                viewModel.onVerifyOtpCode(
                                                    otp = otpText,
                                                    onSuccess = {
                                                        signLoading = false
                                                        navHostController.navigate(
                                                            ScreenRoute.HomeScreen.route
                                                        ) {
                                                            popUpTo(ScreenRoute.HomeScreen.route) {
                                                                inclusive = true
                                                            }
                                                        }
                                                    },
                                                    onError = {
                                                        signLoading = false
                                                        Toast.makeText(
                                                            context,
                                                            viewModel.codeInvalid(context),
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                )
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    viewModel.otpInputValidation(context, otpText),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    },
                                    modifier = Modifier.width(280.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                if (isTimerRunning) {
                                    Row {
                                        if (timeLeft == 0) {
                                            TextBtn(
                                                label = stringResource(R.string.resend_code),
                                                onClick = {
                                                    timeOut = 60 * 1000
                                                    viewModel.onVerifyPhoneNumber(
                                                        activity = activity,
                                                        phoneNumber = fullPhoneNumber
                                                    )
                                                })
                                        } else {
                                            Text(
                                                text = stringResource(
                                                    R.string.resend_code_in_sec,
                                                    timeLeft
                                                )
                                            )
                                        }
                                    }
                                }
                            }

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

            if (confirmDialog) {
                val phoneNumber = fullPhoneNumber.split(" ")
                AlertDialog(
                    onDismissRequest = {
                        confirmDialog = false
                        signLoading = false
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.confirm_to_verify),
                            style = MaterialTheme.typography.h6.copy(
                                fontFamily = CustomFontFamily.BayonRegular,
                            )
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(
                                R.string.phone_dialog_content,
                                phoneNumber[0],
                                viewModel.phoneInputValidation(context, phoneNumber[1])
                                    ?: phoneNumber[1]
                            ),
                            style = MaterialTheme.typography.body2
                        )
                    },
                    confirmButton = {
                        TextButton(
                            enabled = viewModel.phoneInputValidation(context, phoneNumber[1])
                                .isNullOrEmpty(),
                            onClick = {
                                scope.launch {
                                    viewModel.onVerifyPhoneNumber(
                                        activity = activity,
                                        phoneNumber = fullPhoneNumber
                                    )
                                    confirmDialog = false
                                    phoneNumberTextVisible = false
                                    delay(300)
                                    otpTextVisible = true
                                    signLoading = false
                                    isSendVerifyBtn = false
                                    isTimerRunning = true
                                }
                            }
                        ) {
                            Text(
                                stringResource(R.string.ok),
                                style = MaterialTheme.typography.button.copy(
                                    fontFamily = CustomFontFamily.BattambangBold
                                )
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                confirmDialog = false
                                signLoading = false
                            }
                        ) {
                            Text(
                                stringResource(R.string.cancel),
                                style = MaterialTheme.typography.button.copy(
                                    fontFamily = CustomFontFamily.BattambangBold
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}