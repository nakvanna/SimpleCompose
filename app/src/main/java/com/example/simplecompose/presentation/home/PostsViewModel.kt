package com.example.simplecompose.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
    @Named("get_posts") private val postsUseCase: GetPosts,
    @Named("firebase_auth") private val firebaseAuth: FirebaseAuth,
) :
    ViewModel() {
    private val _posts: MutableState<List<PostsQuery.Edge>> = mutableStateOf(ArrayList())
    val posts: State<List<PostsQuery.Edge>> get() = _posts

    private val channel = Channel<Unit>(Channel.CONFLATED)
    private var count = 5

    private val _isFetching = mutableStateOf(false)
    val isFetching: State<Boolean> get() = _isFetching

    private val currentUser = firebaseAuth.currentUser
    private val _displayName = if (!currentUser?.displayName.isNullOrEmpty()) {
        currentUser?.displayName
    } else null
    val displayName get() = _displayName

    private val _providerRef =
        if (firebaseAuth.currentUser?.email.isNullOrEmpty()) firebaseAuth.currentUser!!.phoneNumber else firebaseAuth.currentUser!!.email
    val providerRef get() = _providerRef

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing get(): State<Boolean> = _isRefreshing

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
                    _posts.value = newPosts
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

    fun refresh() {
        // This doesn't handle multiple 'refreshing' tasks, don't use this
        viewModelScope.launch {
            // A fake 2 second 'refresh'
            _isRefreshing.value = true
            delay(2000)
            _isRefreshing.value = false
        }
    }
    
    fun firebaseSignOut() {
        firebaseAuth.signOut()
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