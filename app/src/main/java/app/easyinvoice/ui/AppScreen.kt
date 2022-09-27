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

package app.easyinvoice.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import app.easyinvoice.R
import app.easyinvoice.ui.auth.home.*

sealed class AppScreen(@StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {

    object Auth : AppScreen(R.string.app_name, R.drawable.ic_app_logo, "nav_auth") {
        object Login : AppScreen(R.string.login, R.drawable.ic_app_logo, "login")
        object Signup : AppScreen(R.string.signup, R.drawable.ic_app_logo, "signup")
    }

    object Dashboard : AppScreen(R.string.dashboard, R.drawable.ic_dashboard, "nav_dashboard")

    object Invoices : AppScreen(R.string.invoices, R.drawable.ic_invoices, "nav_invoices") {
        object Home : AppScreen(R.string.invoices, R.drawable.ic_invoices, "invoices")
        object InvoiceDetail : AppScreen(R.string.invoices, R.drawable.ic_invoices, "invoice_detail")
        object ManageInvoice : AppScreen(R.string.invoices, R.drawable.ic_invoices, "manage_invoice") {
            object PickBusiness : AppScreen(R.string.invoices, R.drawable.ic_invoices, "pick_business")
            object PickCustomer : AppScreen(R.string.invoices, R.drawable.ic_invoices, "pick_customer")
            object PickTax : AppScreen(R.string.invoices, R.drawable.ic_invoices, "pick_tax")
            object AddItems : AppScreen(R.string.invoices, R.drawable.ic_invoices, "add_items")
        }
    }

    object Taxes : AppScreen(R.string.taxes, R.drawable.ic_taxes, "nav_taxes") {
        object Home : AppScreen(R.string.taxes, R.drawable.ic_taxes, "taxes")
        object ManageTaxes : AppScreen(R.string.add_tax, R.drawable.ic_taxes, "manage_tax")
    }

    object MyBusinesses : AppScreen(R.string.my_businesses, R.drawable.ic_my_businesses, "nav_businesses") {
        object Home : AppScreen(R.string.my_businesses, R.drawable.ic_my_businesses, "businesses")
        object ManageMyBusiness : AppScreen(R.string.add_business, R.drawable.ic_my_businesses, "manage_business")
    }

    object Customers : AppScreen(R.string.customers, R.drawable.ic_customers, "nav_customers") {
        object Home : AppScreen(R.string.customers, R.drawable.ic_customers, "customers")
        object ManageCustomer : AppScreen(R.string.add_customer, R.drawable.ic_customers, "manage_customer")
    }

    object Logout : AppScreen(R.string.logout, R.drawable.ic_logout, "logout")
}
