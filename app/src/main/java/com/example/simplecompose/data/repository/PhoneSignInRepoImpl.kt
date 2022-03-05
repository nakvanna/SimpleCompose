package com.example.simplecompose.data.repository

import android.app.Activity
import com.example.simplecompose.domain.repository.PhoneSignInRepo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneSignInRepoImpl : PhoneSignInRepo {

    override suspend fun verifyPhoneNumber(
        activity: Activity,
        phoneNumber: String,
        firebaseAuth: FirebaseAuth,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyOtpCode(
        code: String,
        storedVerificationId: String?,
        firebaseAuth: FirebaseAuth
    ): Task<AuthResult> {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
        return firebaseAuth.signInWithCredential(credential)
    }
}