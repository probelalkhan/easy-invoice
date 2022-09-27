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

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.easyinvoice.ui.AppScreen
import app.easyinvoice.ui.auth.home.mybusinesses.ManageMyBusiness
import app.easyinvoice.ui.auth.home.mybusinesses.MyBusinesses
import app.easyinvoice.ui.auth.home.mybusinesses.MyBusinessesViewModel
import app.easyinvoice.ui.utils.getViewModelInstance

fun NavGraphBuilder.businessNav(navController: NavController) {
    navigation(
        startDestination = AppScreen.MyBusinesses.Home.route,
        route = AppScreen.MyBusinesses.route
    ) {

        composable(AppScreen.MyBusinesses.Home.route) {
            val vm = navController.getViewModelInstance<MyBusinessesViewModel>(it, AppScreen.MyBusinesses.route)
            MyBusinesses(vm, navController)
        }

        composable(AppScreen.MyBusinesses.ManageMyBusiness.route) {
            val vm = navController.getViewModelInstance<MyBusinessesViewModel>(it, AppScreen.MyBusinesses.route)
            ManageMyBusiness(vm, navController)
        }

    }
}