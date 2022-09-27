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

package app.easyinvoice.data

import app.easyinvoice.data.models.BaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

abstract class BaseRepository<T : BaseModel>(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore,
    dbNode: String
) {
    private val currentUser = auth.currentUser ?: throw IllegalStateException("User not logged in")
    protected val db = firestore.collection(currentUser.uid).document(dbNode).collection(dbNode)

    fun getData(snapshot: QuerySnapshot, model: Class<T>): List<T> {
        return snapshot.map {
            it.toObject(model).also { model ->
                model.id = it.id
                model.createdAt = it.data["createdAt"].toString().toLong()
                model.updatedAt = it.data["updatedAt"].toString().toLong()
            }
        }
    }
}