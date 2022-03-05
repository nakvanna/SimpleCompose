package com.example.simplecompose.presentation.auth.google

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.simplecompose.R
import com.example.simplecompose.domain.use_case.google.SignInGoogle
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.ui.widget.OutlineButtonWithIcon
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInBtn(
    navHostController: NavHostController,
) {
    val clientId = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    val activity = context as Activity
    val signInGoogle = SignInGoogle(activity = activity, clientId = clientId)
    val scope = rememberCoroutineScope()
    var isLoading by remember {
        mutableStateOf(false)
    }
    // rememberLauncherForActivityResult need to be in Composable function
    val launcherForActivityResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!

                    scope.launch {
                        signInGoogle.firebaseAuthWithCredential(
                            idToken = account.idToken!!,
                        ).addOnCompleteListener(activity) {
                            isLoading = !it.isSuccessful
                            navHostController.navigate(ScreenRoute.HomeScreen.route) {
                                popUpTo(ScreenRoute.HomeScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                } catch (e: ApiException) {
                    isLoading = false
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
            if (activityResult.resultCode == Activity.RESULT_CANCELED) {
                isLoading = false
            }
        }

    suspend fun googleSignIn() {
        launcherForActivityResult.launch(signInGoogle.googleSignInIntent())
    }
    OutlineButtonWithIcon(
        label = stringResource(R.string.sign_with_google),
        loadingLabel = stringResource(R.string.google_signing),
        icon = R.drawable.google,
        onClick = {
            scope.launch {
                isLoading = true
                googleSignIn()
            }
            // navHostController.navigate(ScreenRoute.HomeScreen.route)
        },
        isLoading = isLoading
    )
}