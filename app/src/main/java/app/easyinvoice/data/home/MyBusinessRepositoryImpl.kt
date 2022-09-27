package app.easyinvoice.data.home

import app.easyinvoice.data.BaseRepository
import app.easyinvoice.data.Resource
import app.easyinvoice.data.models.Business
import app.easyinvoice.data.utils.await
import app.easyinvoice.data.utils.currentDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MyBusinessRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : MyBusinessRepository, BaseRepository<Business>(auth, firestore, DB_MY_BUSINESSES) {

    override suspend fun getMyBusinesses(): Resource<List<Business>> {
        return try {
            val snapshot = db.get().await()
            Resource.Success(getData(snapshot, Business::class.java))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun canAddBusiness(): Boolean {
        return db.get().await().size() < MAX_ALLOWED_BUSINESS
    }

    override suspend fun addMyBusiness(business: Business): Resource<Business> {
        return try {
            db.add(business).await()
            Resource.Success(business)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateMyBusiness(business: Business): Resource<Business> {
        return try {
            business.updatedAt = currentDateTime
            db.document(business.id).set(business).await()
            Resource.Success(business)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deleteMyBusiness(id: String): Resource<Boolean> {
        return try {
            db.document(id).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    companion object {
        private const val DB_MY_BUSINESSES = "my_businesses"
        private const val MAX_ALLOWED_BUSINESS = 5
    }
}