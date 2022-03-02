package com.example.simplecompose.domain.use_case.post

import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.example.simplecompose.PostsQuery
import com.example.simplecompose.domain.repository.PostsRepo

class GetPosts(
    private val repo: PostsRepo,
    private val context: Context
) {
    suspend operator fun invoke(count: Int): ApolloResponse<PostsQuery.Data> {
        return repo.fetchPosts(count, context)
    }
}