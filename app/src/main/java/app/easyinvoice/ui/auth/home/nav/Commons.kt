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

import androidx.navigation.NavBackStackEntry
import app.easyinvoice.ui.AppScreen

fun NavBackStackEntry.getTitle(): Int {
    return when (destination.parent?.route) {
        AppScreen.Dashboard.route -> AppScreen.Dashboard.title
        AppScreen.Invoices.route -> AppScreen.Invoices.title
        AppScreen.Taxes.route -> AppScreen.Taxes.title
        AppScreen.MyBusinesses.route -> AppScreen.MyBusinesses.title
        AppScreen.Customers.route -> AppScreen.Customers.title
        else -> AppScreen.Dashboard.title
    }
}