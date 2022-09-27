package app.easyinvoice.data.home

import app.easyinvoice.data.Resource
import app.easyinvoice.data.models.Business

interface MyBusinessRepository {
    suspend fun getMyBusinesses(): Resource<List<Business>>
    suspend fun canAddBusiness(): Boolean
    suspend fun addMyBusiness(business: Business): Resource<Business>
    suspend fun updateMyBusiness(business: Business): Resource<Business>
    suspend fun deleteMyBusiness(id: String): Resource<Boolean>
}