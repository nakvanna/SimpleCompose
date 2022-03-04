package com.example.simplecompose.domain.use_case.phone

import android.app.Activity
import com.example.simplecompose.domain.repository.PhoneSignInRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class VerifyPhoneNumber(
    private val repo: PhoneSignInRepo,
) {
    suspend operator fun invoke(
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        firebaseAuth: FirebaseAuth,
        activity: Activity
    ) {
        return repo.verifyPhoneNumber(
            activity = activity,
            phoneNumber = phoneNumber,
            callbacks = callbacks,
            firebaseAuth = firebaseAuth
        )
    }
}