package com.example.simplecompose.presentation.auth.phone

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import com.example.simplecompose.R
import com.example.simplecompose.core.theme.CustomAnimate
import com.example.simplecompose.core.theme.CustomFontFamily
import com.example.simplecompose.core.theme.SimpleComposeTheme
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.presentation.auth.phone.widget.CountryCodePicker
import com.example.simplecompose.ui.reusable.ImageBackground
import com.example.simplecompose.ui.reusable.PowerByCloudWare
import com.example.simplecompose.ui.widgets.CircleImageRes
import com.example.simplecompose.ui.widgets.OutlineButtonWithIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PhoneSignInScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    var signLoading by remember { mutableStateOf(false) }
    var headerVisible by remember { mutableStateOf(false) }
    var btnVerifyVisible by remember { mutableStateOf(false) }

    var countyCodeSelect by remember { mutableStateOf("+855") }

    var phoneNumberTextVisible by remember { mutableStateOf(false) }
    var phoneNumberIsError by remember { mutableStateOf(true) }
    var phoneNumberText by remember { mutableStateOf("") }
    var phoneNumberValidation: String? by remember { mutableStateOf(null) }

    var otpTextVisible by remember { mutableStateOf(false) }
    var otpIsError by remember { mutableStateOf(true) }
    var otpText by remember { mutableStateOf("") }
    var otpValidation: String? by remember { mutableStateOf(null) }

    var confirmDialog by remember { mutableStateOf(false) }

    var isSendVerifyBtn by remember { mutableStateOf(true) }

    val viewModel = ViewModelProvider(viewModelStoreOwner)[PhoneSignInViewModel::class.java]

    LaunchedEffect(rememberScaffoldState()) {
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
                ImageBackground()
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            CircleImageRes(
                                image = R.drawable.cloudware,
                                modifier = Modifier.size(128.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.sign_in_with_phone),
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.h4.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = CustomFontFamily.AngkorRegular,
                                    background = MaterialTheme.colors.background
                                ),
                            )
                            Text(
                                text = stringResource(R.string.we_happy_to_see_you_here),
                                style = MaterialTheme.typography.subtitle1.copy(
                                    fontFamily = CustomFontFamily.BayonRegular,
                                    background = MaterialTheme.colors.background
                                )
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
                            visible = phoneNumberTextVisible,
                            enter = CustomAnimate.slideInLeft,
                            exit = CustomAnimate.slideOutRight,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.padding(top = 8.dp)) {
                                    Column {
                                        CountryCodePicker {
                                            countyCodeSelect = it
                                        }
                                        if (phoneNumberIsError) {
                                            Spacer(modifier = Modifier.height(16.dp))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Column {
                                    OutlinedTextField(
                                        value = phoneNumberText,
                                        onValueChange = {
                                            phoneNumberText = it
                                            phoneNumberIsError =
                                                !viewModel.phoneInputValidation(it).isNullOrEmpty()
                                            phoneNumberValidation =
                                                viewModel.phoneInputValidation(it)
                                        },
                                        shape = MaterialTheme.shapes.small,
                                        label = { Text(stringResource(R.string.phone_number)) },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                        singleLine = true,
                                        isError = phoneNumberIsError,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                        trailingIcon = {
                                            if (phoneNumberIsError)
                                                Icon(
                                                    Icons.Default.Warning,
                                                    "error",
                                                    tint = MaterialTheme.colors.error,
                                                )
                                        },
                                    )
                                    if (phoneNumberIsError) {
                                        Text(
                                            text = phoneNumberValidation
                                                ?: stringResource(R.string.require),
                                            color = MaterialTheme.colors.error,
                                            style = MaterialTheme.typography.caption,
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                        )
                                    }
                                }
                            }
                        }
                        AnimatedVisibility(
                            visible = otpTextVisible,
                            enter = CustomAnimate.slideInLeft,
                            exit = CustomAnimate.slideOutRight
                        ) {
                            Column {
                                OutlinedTextField(
                                    value = otpText,
                                    onValueChange = {
                                        otpText = it
                                        otpIsError =
                                            !viewModel.otpInputValidation(it).isNullOrEmpty()
                                        otpValidation =
                                            viewModel.otpInputValidation(it)
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    shape = MaterialTheme.shapes.small,
                                    label = { Text(stringResource(R.string.opt_verify_code)) },
                                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    modifier = Modifier.background(MaterialTheme.colors.background),
                                    trailingIcon = {
                                        if (otpIsError)
                                            Icon(
                                                Icons.Default.Warning,
                                                "error",
                                                tint = MaterialTheme.colors.error,
                                            )
                                    },
                                )
                                if (otpIsError) {
                                    Text(
                                        text = otpValidation ?: stringResource(R.string.require),
                                        color = MaterialTheme.colors.error,
                                        style = MaterialTheme.typography.caption,
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        AnimatedVisibility(
                            visible = btnVerifyVisible,
                            enter = CustomAnimate.slideInRight,
                            exit = CustomAnimate.slideOutRight,
                            content = {
                                OutlineButtonWithIcon(
                                    label = if (isSendVerifyBtn) stringResource(R.string.send_verify_code) else stringResource(
                                        R.string.opt_verify_code
                                    ),
                                    icon = R.drawable.phone,
                                    isLoading = signLoading,
                                    onClick = {
                                        scope.launch {
                                            if (isSendVerifyBtn) {
                                                confirmDialog = true
                                                signLoading = true
                                            } else {
                                                signLoading = true
                                                if (!otpIsError) {
                                                    viewModel.onVerifyOtpCode(otpText)
                                                        .addOnCompleteListener {
                                                            if (it.isSuccessful) {
                                                                signLoading = false
                                                                navHostController.navigate(
                                                                    ScreenRoute.HomeScreen.route
                                                                ) {
                                                                    popUpTo(ScreenRoute.HomeScreen.route) {
                                                                        inclusive = true
                                                                    }
                                                                }
                                                            } else {
                                                                signLoading = false
                                                                Toast.makeText(
                                                                    context,
                                                                    "Code invalid",
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                            }
                                                        }
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier.width(280.dp)
                                )
                            }
                        )
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
                                countyCodeSelect,
                                phoneNumberValidation ?: phoneNumberText
                            ),
                            style = MaterialTheme.typography.body2
                        )
                    },
                    confirmButton = {
                        TextButton(
                            enabled = !phoneNumberIsError,
                            onClick = {
                                scope.launch {
                                    viewModel.onVerifyPhoneNumber(
                                        activity = activity,
                                        phoneNumber = "$countyCodeSelect $phoneNumberText"
                                    )
                                    confirmDialog = false
                                    phoneNumberTextVisible = false
                                    delay(300)
                                    otpTextVisible = true
                                    signLoading = false
                                    isSendVerifyBtn = false
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