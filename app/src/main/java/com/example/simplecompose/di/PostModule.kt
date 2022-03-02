package com.example.simplecompose.di

import android.content.Context
import com.example.simplecompose.data.repository.PostsRepoImpl
import com.example.simplecompose.domain.use_case.post.GetPosts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {
    @Singleton
    @Provides
    @Named("get_posts")
    fun provideGetPosts(@ApplicationContext context: Context) =
        GetPosts(repo = PostsRepoImpl(), context = context)
}