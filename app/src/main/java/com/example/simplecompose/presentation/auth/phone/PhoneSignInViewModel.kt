package com.example.simplecompose.presentation.auth.phone

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecompose.R
import com.example.simplecompose.domain.use_case.phone.VerifyOtpCode
import com.example.simplecompose.domain.use_case.phone.VerifyPhoneNumber
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PhoneSignInViewModel @Inject constructor(
    @Named("verify_phone_number") private val verifyPhoneNumberUseCase: VerifyPhoneNumber,
    @Named("verify_otp_code") private val verifyOtpCodeUseCase: VerifyOtpCode,
    @Named("firebase_auth") private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    init {
        firebaseAuth.setLanguageCode("en")

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                /* This callback will be invoked in two situations:
                 1 - Instant verification. In some cases the phone number can be instantly
                     verified without needing to send or enter a verification code.
                 2 - Auto-retrieval. On some devices Google Play services can automatically
                     detect the incoming verification SMS and perform verification without
                     user action.*/
                Log.d("Phone", "onVerificationCompleted:$credential")
                viewModelScope.launch {
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.i("Phone", "Auto sign in with credential successful")
                        } else {
                            Log.i("Phone", "Auto sign in with credential failure")
                        }
                    }
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.i("Phone", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.i("Phone", "Invalid request")
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.i("Phone", "The SMS quota for the project has been exceeded")
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.i("Phone", "onCodeSent: $verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    fun onVerifyPhoneNumber(activity: Activity, phoneNumber: String) {
        viewModelScope.launch {
            verifyPhoneNumberUseCase(
                phoneNumber = phoneNumber,
                callbacks = callbacks,
                firebaseAuth = firebaseAuth,
                activity = activity
            )
        }
    }

    fun onVerifyOtpCode(otp: String, onSuccess: () -> Unit = {}, onError: () -> Unit = {}) {
        viewModelScope.launch {
            verifyOtpCodeUseCase(
                storedVerificationId = storedVerificationId,
                code = otp,
                firebaseAuth = firebaseAuth
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }
        }
    }


    fun phoneInputValidation(context: Context, value: String): String? {
        val validRange = value.length !in 9..10
        if (!Patterns.PHONE.matcher(value).matches()) {
            return context.getString(R.string.invalid_phone_number)
        } else if (validRange) {
            return context.getString(R.string.must_be_9_or_10_char)
        }
        return null
    }

    fun otpInputValidation(context: Context, value: String): String? {
        val validLength = value.length == 6
        if (!validLength) {
            return context.getString(R.string.code_must_be_6_char)
        }
        return null
    }
}