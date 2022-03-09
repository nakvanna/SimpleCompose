package com.example.simplecompose.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.simplecompose.core.theme.SimpleComposeTheme
import com.example.simplecompose.core.util.HOST
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.presentation.home.widget.PostListItemCard
import com.example.simplecompose.ui.widget.OverflowMenu
import com.example.simplecompose.ui.widget.SimpleComposeDrawer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: PostsViewModel = hiltViewModel()
) {
    SimpleComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        val isRefreshing by viewModel.isRefreshing

        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                SimpleComposeDrawer(
                    state = scaffoldState,
                    navHostController = navHostController,
                    displayName = viewModel.displayName ?: "Not Provide",
                    providerRef = viewModel.providerRef ?: "Nothing",
                    tokenId = "Token"
                )
            },
            topBar = {
                TopAppBar(
                    title = { Text(text = "Home") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        OverflowMenu(navHostController, signOut = {
                            viewModel.firebaseSignOut()
                            navHostController.navigate(ScreenRoute.AuthScreen.route) {
                                popUpTo(ScreenRoute.HomeScreen.route) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            if (!viewModel.posts.value.isNullOrEmpty()) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = {
                        viewModel.refresh()
                    }) {
                    LazyColumn {
                        itemsIndexed(viewModel.posts.value) { index, item ->
                            viewModel.onScrollingPositionChange(index)
                            PostListItemCard(
                                image = HOST + item.node?.mediaPosts?.get(0)?.fileUrl,
                                title = item.node?.title ?: "No title",
                                content = item.node?.content ?: "No content",
                            ) {

                            }
                        }
                        if (viewModel.isFetching.value) {
                            item {
                                Box(
                                    contentAlignment = Alignment.BottomCenter,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Empty content")

                }
            }
        }
    }
}