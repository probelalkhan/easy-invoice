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
import app.easyinvoice.data.models.Customer
import app.easyinvoice.data.utils.await
import app.easyinvoice.data.utils.currentDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CustomersRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : CustomersRepository, BaseRepository<Customer>(auth, firestore, DB_CUSTOMERS) {

    override suspend fun getCustomers(): Resource<List<Customer>> {
        return try {
            val snapshot = db.get().await()
            Resource.Success(getData(snapshot, Customer::class.java))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addCustomer(customer: Customer): Resource<Customer> {
        return try {
            db.add(customer).await()
            Resource.Success(customer)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateCustomer(customer: Customer): Resource<Customer> {
        return try {
            customer.updatedAt = currentDateTime
            db.document(customer.id).set(customer).await()
            Resource.Success(customer)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deleteCustomer(id: String): Resource<Boolean> {
        return try {
            db.document(id).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    companion object {
        private const val DB_CUSTOMERS = "customers"
    }
}