package app.easyinvoice.ui.auth.home

import android.os.Bundle
import androidx.activity.compose.setContent
import app.easyinvoice.ui.BaseActivity
import app.easyinvoice.ui.auth.home.nav.HomeNavHost
import app.easyinvoice.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                HomeNavHost()
            }
        }
    }
}