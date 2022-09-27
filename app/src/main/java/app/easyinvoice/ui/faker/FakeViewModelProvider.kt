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

package app.easyinvoice.ui.faker

import app.easyinvoice.data.Resource
import app.easyinvoice.data.auth.AuthRepository
import app.easyinvoice.data.home.*
import app.easyinvoice.data.models.*
import app.easyinvoice.ui.auth.AuthViewModel
import app.easyinvoice.ui.auth.home.customers.CustomersViewModel
import app.easyinvoice.ui.auth.home.dashboard.DashboardViewModel
import app.easyinvoice.ui.auth.home.invoices.InvoicesViewModel
import app.easyinvoice.ui.auth.home.mybusinesses.MyBusinessesViewModel
import app.easyinvoice.ui.auth.home.taxes.TaxesViewModel
import com.google.firebase.auth.FirebaseUser

/*
* Currently there is a problem with *Jetpack Compose Preview* & *Hilt*
* Jetpack compose is not able to inject using hiltViewModel() to generate Compose Previews
* In future when both these libraries will be compatible, we can remove this object
* But for now, to see preview, we can use this FakeViewModelProvider
* */
object FakeViewModelProvider {

    fun provideDashboardViewModel() = DashboardViewModel(dashboardRepo)

    fun provideInvoicesViewModel() = InvoicesViewModel(invoiceRepo, businessRepo, customersRepo, taxRepo)

    fun provideTaxesViewModel() = TaxesViewModel(taxRepo)

    fun provideCustomersViewModel() = CustomersViewModel(customersRepo)

    fun provideMyBusinessesViewModel() = MyBusinessesViewModel(businessRepo)

    fun provideAuthViewModel() = AuthViewModel(authRepo)

    private val dashboardRepo = object : DashboardRepository {
        override suspend fun getDashboardInfo(): Resource<Dashboard> {
            TODO("Not yet implemented")
        }
    }

    private val invoiceRepo = object : InvoiceRepository {
        override suspend fun getInvoices(): Resource<List<Invoice>> {
            TODO("Not yet implemented")
        }

        override suspend fun addInvoice(invoice: Invoice): Resource<Invoice> {
            TODO("Not yet implemented")
        }

        override suspend fun updateInvoice(invoice: Invoice): Resource<Invoice> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteInvoice(id: String): Resource<Boolean> {
            TODO("Not yet implemented")
        }

        override suspend fun updatePaidState(id: String, isPaid: Boolean): Resource<Boolean> {
            TODO("Not yet implemented")
        }

    }

    private val taxRepo = object : TaxRepository {
        override suspend fun getTaxes(): Resource<List<Tax>> {
            TODO("Not yet implemented")
        }

        override suspend fun addTax(tax: Tax): Resource<Tax> {
            TODO("Not yet implemented")
        }

        override suspend fun updateTax(tax: Tax): Resource<Tax> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteTax(id: String): Resource<Boolean> {
            TODO("Not yet implemented")
        }

    }

    private val customersRepo = object : CustomersRepository {
        override suspend fun getCustomers(): Resource<List<Customer>> {
            TODO("Not yet implemented")
        }

        override suspend fun addCustomer(customer: Customer): Resource<Customer> {
            TODO("Not yet implemented")
        }

        override suspend fun updateCustomer(customer: Customer): Resource<Customer> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteCustomer(id: String): Resource<Boolean> {
            TODO("Not yet implemented")
        }

    }

    private val businessRepo = object : MyBusinessRepository {
        override suspend fun getMyBusinesses(): Resource<List<Business>> {
            TODO("Not yet implemented")
        }

        override suspend fun canAddBusiness(): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun addMyBusiness(business: Business): Resource<Business> {
            TODO("Not yet implemented")
        }

        override suspend fun updateMyBusiness(business: Business): Resource<Business> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteMyBusiness(id: String): Resource<Boolean> {
            TODO("Not yet implemented")
        }
    }

    private val authRepo = object : AuthRepository {
        override val currentUser: FirebaseUser?
            get() = null

        override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
            TODO("Not yet implemented")
        }

        override suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
            TODO("Not yet implemented")
        }

        override fun logout() {
            TODO("Not yet implemented")
        }

    }
}