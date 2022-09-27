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

package app.easyinvoice.data.home

import app.easyinvoice.data.Resource
import app.easyinvoice.data.models.Dashboard
import app.easyinvoice.data.models.Invoice
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val auth: FirebaseAuth
) : DashboardRepository {

    override suspend fun getDashboardInfo(): Resource<Dashboard> {
        val invoices = invoiceRepository.getInvoices()
        return try {
            Resource.Success(getDashboardInfo((invoices as Resource.Success).result))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    private fun getDashboardInfo(invoices: List<Invoice>) = Dashboard(
        invoiceCount = invoices.size,
        receivedAmount = invoices.filter { it.isPaid() }.sumOf { it.invoiceAmount },
        totalAmount = invoices.sumOf { it.invoiceAmount },
        pendingInvoices = invoices.count { !it.isPaid() },
        pendingAmount = invoices.filter { !it.isPaid() }.sumOf { it.invoiceAmount },
    )
}