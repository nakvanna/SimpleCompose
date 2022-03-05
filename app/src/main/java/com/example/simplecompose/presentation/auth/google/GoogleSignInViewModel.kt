package com.example.simplecompose.presentation.auth.google

import androidx.lifecycle.ViewModel
import com.example.simplecompose.domain.use_case.google.FirebaseAuthWithCredential
import com.example.simplecompose.domain.use_case.google.GoogleSignInIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    @Named("google_sign_in_intent") val googleSignInIntent: GoogleSignInIntent,
    @Named("firebase_auth_with_credential") val firebaseAuthWithCredential: FirebaseAuthWithCredential,
) : ViewModel() 