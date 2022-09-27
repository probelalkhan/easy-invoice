package app.easyinvoice.ui.auth.home

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import app.easyinvoice.R
import app.easyinvoice.ui.theme.AppTheme
import com.google.accompanist.insets.ui.TopAppBar

@Composable
fun TopBar(
    @StringRes title: Int,
    buttonIcon: ImageVector = Icons.Filled.Menu,
    onButtonClicked: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { onButtonClicked() }) {
                    Icon(
                        imageVector = buttonIcon,
                        contentDescription = stringResource(id = R.string.empty),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            backgroundColor = MaterialTheme.colorScheme.primary
        )
        content.invoke()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TopBarPreviewLight() {
    AppTheme {
        TopBar(title = R.string.dashboard, onButtonClicked = { }) { }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBarPreviewDark() {
    AppTheme {
        TopBar(title = R.string.dashboard, onButtonClicked = { }) { }
    }
}
