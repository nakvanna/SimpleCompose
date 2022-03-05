import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SimpleComposeAppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    navHostController: NavHostController
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = actions,
        navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back Button")
            }
        },
    )
}


