package com.example.simplecompose.presentation.auth.phone

import android.app.Activity
import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecompose.R
import com.example.simplecompose.domain.repository.SignInPhoneRepo
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PhoneSignInViewModel(application: Application) : AndroidViewModel(application),
    SignInPhoneRepo {
    private val context by lazy { getApplication<Application>().applicationContext }
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    init {
        auth.setLanguageCode("en")

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                /* This callback will be invoked in two situations:
                 1 - Instant verification. In some cases the phone number can be instantly
                     verified without needing to send or enter a verification code.
                 2 - Auto-retrieval. On some devices Google Play services can automatically
                     detect the incoming verification SMS and perform verification without
                     user action.*/
                Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                viewModelScope.launch {
                    signInWithPhoneAuthCredential(credential)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(ContentValues.TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    makeToast("Invalid request")
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    makeToast("The SMS quota for the project has been exceeded")
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
                Log.d(ContentValues.TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): Task<AuthResult> {
        return auth.signInWithCredential(credential)
    }

    override suspend fun onVerifyPhoneNumber(activity: Activity, phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun onVerifyOtpCode(code: String): Task<AuthResult> {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
        return signInWithPhoneAuthCredential(credential)
    }

    override fun phoneInputValidation(value: String): String? {
        val validRange = value.length !in 9..10
        if (!Patterns.PHONE.matcher(value).matches()) {
            return context.getString(R.string.invalid_phone_number)
        } else if (validRange) {
            return context.getString(R.string.must_be_9_or_10_char)
        }
        return null
    }

    override fun otpInputValidation(value: String): String? {
        val validLength = value.length == 6
        if (!validLength) {
            return context.getString(R.string.code_must_be_6_char)
        }
        return null
    }
}