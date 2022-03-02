package com.example.simplecompose.data.repository

import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.simplecompose.PostsQuery
import com.example.simplecompose.core.apollo.apolloClient
import com.example.simplecompose.domain.repository.PostsRepo
import com.example.simplecompose.type.ArgCondition

class PostsRepoImpl : PostsRepo {
    override suspend fun fetchPosts(count: Int, context: Context): ApolloResponse<PostsQuery.Data> {
        return apolloClient(context).query(
            PostsQuery(
                first = count,
                condition = "{}",
                userArg = ArgCondition(condition = Optional.presentIfNotNull("{}"))
            )
        ).execute()
    }
}