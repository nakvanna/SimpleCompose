package com.example.simplecompose.di

import com.example.simplecompose.data.repository.PhoneSignInRepoImpl
import com.example.simplecompose.domain.use_case.phone.VerifyOtpCode
import com.example.simplecompose.domain.use_case.phone.VerifyPhoneNumber
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhoneSignInModule {

    @Singleton
    @Provides
    @Named("verify_phone_number")
    fun provideVerifyPhoneNumber(): VerifyPhoneNumber {
        return VerifyPhoneNumber(
            repo = PhoneSignInRepoImpl()
        )
    }


    @Singleton
    @Provides
    @Named("verify_otp_code")
    fun provideVerifyOtpCode() = VerifyOtpCode(repo = PhoneSignInRepoImpl())
}