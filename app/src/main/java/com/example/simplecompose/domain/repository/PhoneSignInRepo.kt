package com.example.simplecompose.domain.repository

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

interface PhoneSignInRepo {
    suspend fun verifyPhoneNumber(
        activity: Activity,
        phoneNumber: String,
        firebaseAuth: FirebaseAuth,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    suspend fun verifyOtpCode(
        code: String,
        storedVerificationId: String?,
        firebaseAuth: FirebaseAuth
    ): Task<AuthResult>
}