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
import app.easyinvoice.data.models.Tax
import app.easyinvoice.data.utils.await
import app.easyinvoice.data.utils.currentDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class TaxRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : TaxRepository, BaseRepository<Tax>(auth, firestore, DB_TAXES) {

    override suspend fun getTaxes(): Resource<List<Tax>> {
        return try {
            val snapshot = db.get().await()
            Resource.Success(getData(snapshot, Tax::class.java))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addTax(tax: Tax): Resource<Tax> {
        return try {
            db.add(tax).await()
            Resource.Success(tax)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateTax(tax: Tax): Resource<Tax> {
        return try {
            tax.updatedAt = currentDateTime
            db.document(tax.id).set(tax).await()
            Resource.Success(tax)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deleteTax(id: String): Resource<Boolean> {
        return try {
            db.document(id).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    companion object {
        private const val DB_TAXES = "taxes"
    }
}