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
import app.easyinvoice.ui.auth.home.invoices.*
import app.easyinvoice.ui.auth.home.invoices.detail.InvoiceDetail
import app.easyinvoice.ui.utils.getViewModelInstance


fun NavGraphBuilder.invoiceNav(navController: NavController) {
    navigation(
        startDestination = AppScreen.Invoices.Home.route,
        route = AppScreen.Invoices.route
    ) {
        composable(AppScreen.Invoices.Home.route) {
            val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
            InvoicesScreen(vm, navController)
        }

        composable(AppScreen.Invoices.InvoiceDetail.route) {
            val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
            InvoiceDetail(vm, navController)
        }

        navigation(
            startDestination = AppScreen.Invoices.ManageInvoice.PickBusiness.route,
            route = AppScreen.Invoices.ManageInvoice.route
        ) {

            composable(AppScreen.Invoices.ManageInvoice.PickBusiness.route) {
                val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                PickBusinessScreen(vm, navController)
            }

            composable(AppScreen.Invoices.ManageInvoice.PickCustomer.route) {
                val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                PickCustomerScreen(vm, navController)
            }

            composable(AppScreen.Invoices.ManageInvoice.PickTax.route) {
                val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                PickTaxScreen(vm, navController)
            }

            composable(AppScreen.Invoices.ManageInvoice.AddItems.route) {
                val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                AddInvoiceItem(vm, navController)
            }
        }
    }
}