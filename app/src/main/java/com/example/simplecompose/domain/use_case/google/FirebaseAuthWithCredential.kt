package com.example.simplecompose.domain.use_case.google

import com.example.simplecompose.domain.repository.GoogleSignInRepo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthWithCredential(
    private val repo: GoogleSignInRepo,
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(
        idToken: String
    ): Task<AuthResult> {
        return repo.firebaseAuthWithCredential(
            auth = auth,
            idToken = idToken,
        )
    }
}