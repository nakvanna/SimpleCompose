package com.example.simplecompose.domain.use_case.google

import android.app.Activity
import android.content.Intent
import com.example.simplecompose.domain.repository.SignInGoogleRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInGoogle(
    private val activity: Activity,
    private val clientId: String
) : SignInGoogleRepo {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun googleSignInIntent(): Intent {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, gso).signInIntent
    }

    override suspend fun firebaseAuthWithCredential(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }

}