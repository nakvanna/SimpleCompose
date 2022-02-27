package com.example.simplecompose.domain.repository

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential

interface SignInPhoneRepo {
    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): Task<AuthResult>
    suspend fun onVerifyPhoneNumber(activity: Activity, phoneNumber: String)
    suspend fun onVerifyOtpCode(code: String): Task<AuthResult>
    fun phoneInputValidation(value: String): String?
    fun otpInputValidation(value: String): String?
}