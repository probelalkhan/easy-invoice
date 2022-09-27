package app.easyinvoice.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val xLarge: Dp = 64.dp,
    val xxLarge: Dp = 96.dp,
    val xxxLarge: Dp = 128.dp,

    val drawerWidth: Dp = 360.dp,
    val drawerIconSize: Dp = 24.dp,
    val drawerItemHeight: Dp = 56.dp,
    val drawerLeftPadding: Dp = 28.dp,
    val drawerRightPadding: Dp = 28.dp,
    val drawerItemPadding: Dp = 12.dp,
    val drawerCornerRadius: Dp = 28.dp,
    val drawerIconPadding: Dp = 16.dp,

    val invoiceRowHeight: Dp = 52.dp
)


val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

