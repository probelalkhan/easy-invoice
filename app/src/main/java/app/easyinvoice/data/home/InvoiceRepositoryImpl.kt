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

import app.easyinvoice.data.BaseRepository
import app.easyinvoice.data.Resource
import app.easyinvoice.data.models.Invoice
import app.easyinvoice.data.utils.await
import app.easyinvoice.data.utils.currentDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : InvoiceRepository, BaseRepository<Invoice>(auth, firestore, DB_INVOICES) {

    override suspend fun getInvoices(): Resource<List<Invoice>> {
        return try {
            val snapshot = db.get().await()
            Resource.Success(getData(snapshot, Invoice::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun addInvoice(invoice: Invoice): Resource<Invoice> {
        return try {
            db.add(invoice).await()
            Resource.Success(invoice)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateInvoice(invoice: Invoice): Resource<Invoice> {
        return try {
            invoice.updatedAt = currentDateTime
            db.document(invoice.id).set(invoice).await()
            Resource.Success(invoice)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun deleteInvoice(id: String): Resource<Boolean> {
        return try {
            db.document(id).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updatePaidState(id: String, isPaid: Boolean): Resource<Boolean> {
        return try {
            db.document(id).update(FIELD_IS_PAID, isPaid).await()
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    companion object {
        private const val DB_INVOICES = "invoices"
        private const val FIELD_IS_PAID = "isPaid"
    }
}