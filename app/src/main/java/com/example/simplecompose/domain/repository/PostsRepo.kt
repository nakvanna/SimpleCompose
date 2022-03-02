package com.example.simplecompose.domain.repository

import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.example.simplecompose.PostsQuery

interface PostsRepo {
    suspend fun fetchPosts(count: Int, context: Context): ApolloResponse<PostsQuery.Data>
}