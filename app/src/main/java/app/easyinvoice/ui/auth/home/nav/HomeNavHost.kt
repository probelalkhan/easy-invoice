/*
 * Copyright (c) 2022. Belal Khan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package app.easyinvoice.ui.auth.home.nav

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.easyinvoice.ui.AppScreen
import app.easyinvoice.ui.auth.AuthActivity
import app.easyinvoice.ui.auth.AuthViewModel
import app.easyinvoice.ui.auth.drawer.Drawer
import app.easyinvoice.ui.auth.home.TopBar
import app.easyinvoice.ui.auth.home.dashboard.DashboardScreen
import app.easyinvoice.ui.auth.home.dashboard.DashboardViewModel
import app.easyinvoice.ui.theme.AppTheme
import app.easyinvoice.ui.utils.startNewActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavHost() {
    val context = LocalContext.current
    val title = remember { mutableStateOf(AppScreen.Dashboard.title) }
    val navController = rememberNavController()

    val viewModel: AuthViewModel = hiltViewModel()

    Surface(color = MaterialTheme.colorScheme.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Drawer(
                    navController = navController,
                    onDestinationClicked = { route ->
                        scope.launch { drawerState.close() }
                        if (route == AppScreen.Logout.route) {
                            viewModel.logout()
                            context.startNewActivity(AuthActivity::class.java)
                        } else {
                            navController.navigate(route) {
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        ) {
            TopBar(
                title = title.value,
                onButtonClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                }, content = {
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.Dashboard.route
                    ) {
                        composable(AppScreen.Dashboard.route) { DashboardScreen(hiltViewModel()) }
                        invoiceNav(navController)
                        taxNav(navController)
                        businessNav(navController)
                        customersNav(navController)
                    }
                }
            )
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            title.value = backStackEntry.getTitle()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AppMainScreenPreviewLight() {
    AppTheme {
        HomeNavHost()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppMainScreenPreviewDark() {
    AppTheme {
        HomeNavHost()
    }
}