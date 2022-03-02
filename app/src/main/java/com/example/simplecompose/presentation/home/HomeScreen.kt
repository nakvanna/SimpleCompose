package com.example.simplecompose.presentation.home

import android.text.Html.fromHtml
import android.text.TextUtils
import android.util.TypedValue
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.simplecompose.R
import com.example.simplecompose.core.theme.CustomFontFamily
import com.example.simplecompose.core.theme.SimpleComposeTheme
import com.example.simplecompose.core.util.HOST
import com.example.simplecompose.presentation.ScreenRoute
import com.example.simplecompose.ui.widgets.OverflowMenu
import com.example.simplecompose.ui.widgets.SimpleComposeDrawer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: PostsViewModel = hiltViewModel()
) {
    SimpleComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()

        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                SimpleComposeDrawer(
                    scope = scope,
                    state = scaffoldState,
                    navHostController = navHostController,
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
                            navHostController.navigate(ScreenRoute.LoginScreen.route) {
                                popUpTo(ScreenRoute.LoginScreen.route) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = scrollState,
            ) {
                itemsIndexed(viewModel.posts.value) { index, item ->
                    viewModel.onScrollingPositionChange(index)
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(88.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                top = 16.dp,
                                bottom = 16.dp,
                            )
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = HOST + item.node?.mediaPosts?.get(
                                        0
                                    )?.fileUrl,
                                    builder = {
                                        transformations(RoundedCornersTransformation())
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(width = 100.dp, height = 56.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Column {
                                Text(
                                    text = item.node?.title
                                        ?: "No title",
                                    style = MaterialTheme.typography.subtitle1.copy(
                                        fontFamily = CustomFontFamily.BattambangRegular
                                    )
                                )
                                //Integrate TextView from XML
                                AndroidView(
                                    factory = {
                                        TextView(it)
                                    }
                                ) { textView ->
                                    textView.apply {
                                        text = fromHtml(
                                            item.node?.content
                                                ?: "No title",
                                        )
                                        maxLines = 2
                                        ellipsize = TextUtils.TruncateAt.END
                                        setTextColor(
                                            ContextCompat.getColor(
                                                context,
                                                R.color.gray
                                            )
                                        )
                                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                                    }

                                }
                            }
                        }
                    }
                }

                if (viewModel.isFetching) {
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
    }
}