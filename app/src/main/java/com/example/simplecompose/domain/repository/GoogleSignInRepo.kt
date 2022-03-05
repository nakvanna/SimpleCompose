package com.example.simplecompose.domain.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface GoogleSignInRepo {
    suspend fun googleSignInIntent(context: Context, activity: Activity): Intent
    suspend fun firebaseAuthWithCredential(auth: FirebaseAuth, idToken: String): Task<AuthResult>
}