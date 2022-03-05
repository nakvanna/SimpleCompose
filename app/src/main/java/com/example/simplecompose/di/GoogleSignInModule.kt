package com.example.simplecompose.di

import android.content.Context
import com.example.simplecompose.data.repository.GoogleSignInRepoImpl
import com.example.simplecompose.domain.use_case.google.FirebaseAuthWithCredential
import com.example.simplecompose.domain.use_case.google.GoogleSignInIntent
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleSignInModule {
    @Singleton
    @Provides
    @Named("google_sign_in_intent")
    fun provideGoogleSignInIntent(@ApplicationContext context: Context): GoogleSignInIntent {
        return GoogleSignInIntent(
            repo = GoogleSignInRepoImpl(),
            context = context
        )
    }

    @Singleton
    @Provides
    @Named("firebase_auth_with_credential")
    fun provideFirebaseAuthWithCredential(
        @Named("firebase_auth") firebaseAuth: FirebaseAuth
    ): FirebaseAuthWithCredential {
        return FirebaseAuthWithCredential(
            repo = GoogleSignInRepoImpl(),
            auth = firebaseAuth
        )
    }
}