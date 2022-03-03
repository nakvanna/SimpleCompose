package com.example.simplecompose.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.simplecompose.PostsQuery
import com.example.simplecompose.domain.use_case.post.GetPosts
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PostsViewModel @Inject constructor(
    @Named("get_posts") postsUseCase: GetPosts,
    @Named("firebase_auth") firebaseAuth: FirebaseAuth,
) :
    ViewModel() {
    private val _posts: MutableState<List<PostsQuery.Edge>> = mutableStateOf(ArrayList())
    val posts get() = _posts

    private val channel = Channel<Unit>(Channel.CONFLATED)
    private var count = 5

    private val _isFetching = mutableStateOf(false)
    val isFetching get() = _isFetching.value

    private val auth = firebaseAuth

    init {
        channel.trySend(Unit)
        viewModelScope.launch {
            for (item in channel) {
                Log.d("QR", "Request")
                val response = try {
                    postsUseCase(count)
                } catch (e: ApolloException) {
                    Log.d("QR", "Failure", e)
                    return@launch
                }
                val newPosts = response.data?.posts?.edges?.filterNotNull()
                if (newPosts != null) {
                    posts.value = newPosts
                    _isFetching.value = false
                }
                if (count >= response.data?.posts?.count ?: 5) {
                    Log.d("QR", "Breaking")
                    channel.close()
                    break
                }
            }
        }
    }

    fun firebaseSignOut() {
        auth.signOut()
    }

    fun onScrollingPositionChange(index: Int) {
        if (index == (count - 1) && !_isFetching.value) {
            _isFetching.value = true
            viewModelScope.launch {
                delay(2000)
                Log.d("QR", "Increment count")
                count += 5 //Good for graphql pagination
                channel.trySend(Unit)
            }
        }
    }
}