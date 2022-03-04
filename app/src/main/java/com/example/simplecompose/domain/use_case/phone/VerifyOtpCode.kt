package com.example.simplecompose.domain.use_case.phone

import com.example.simplecompose.domain.repository.PhoneSignInRepo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class VerifyOtpCode(
    private val repo: PhoneSignInRepo,
) {
    suspend operator fun invoke(
        code: String,
        storedVerificationId: String?,
        firebaseAuth: FirebaseAuth
    ): Task<AuthResult> {
        return repo.verifyOtpCode(
            firebaseAuth = firebaseAuth,
            code = code,
            storedVerificationId = storedVerificationId,
        )
    }
}