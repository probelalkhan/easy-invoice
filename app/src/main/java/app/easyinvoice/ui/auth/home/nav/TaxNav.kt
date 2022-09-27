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
import app.easyinvoice.ui.auth.home.taxes.ManageTaxes
import app.easyinvoice.ui.auth.home.taxes.Taxes
import app.easyinvoice.ui.auth.home.taxes.TaxesViewModel
import app.easyinvoice.ui.utils.getViewModelInstance

fun NavGraphBuilder.taxNav(navController: NavController) {
    navigation(
        startDestination = AppScreen.Taxes.Home.route,
        route = AppScreen.Taxes.route
    ) {
        composable(AppScreen.Taxes.Home.route) {
            val vm = navController.getViewModelInstance<TaxesViewModel>(it, AppScreen.Taxes.route)
            Taxes(vm, navController)
        }

        composable(AppScreen.Taxes.ManageTaxes.route) {
            val vm = navController.getViewModelInstance<TaxesViewModel>(it, AppScreen.Taxes.route)
            ManageTaxes(vm, navController)
        }
    }
}