package com.example.simplecompose.data.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.simplecompose.R
import com.example.simplecompose.domain.repository.GoogleSignInRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInRepoImpl : GoogleSignInRepo {
    override suspend fun googleSignInIntent(context: Context, activity: Activity): Intent {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, gso).signInIntent
    }

    override suspend fun firebaseAuthWithCredential(
        auth: FirebaseAuth,
        idToken: String
    ): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }
}