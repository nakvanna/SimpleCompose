package com.example.simplecompose.domain.use_case.google

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.simplecompose.domain.repository.GoogleSignInRepo

class GoogleSignInIntent(
    private val repo: GoogleSignInRepo,
    private val context: Context,
) {
    suspend operator fun invoke(
        activity: Activity
    ): Intent {
        return repo.googleSignInIntent(
            context = context,
            activity = activity,
        )
    }
}