package com.example.simplecompose.domain.repository

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface SignInGoogleRepo {
    suspend fun googleSignInIntent(): Intent
    suspend fun firebaseAuthWithCredential(idToken: String): Task<AuthResult>
}