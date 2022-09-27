package app.easyinvoice.ui.auth.home.mybusinesses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.easyinvoice.data.Resource
import app.easyinvoice.data.home.MyBusinessRepository
import app.easyinvoice.data.models.Business
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class MyBusinessesViewModel @Inject constructor(
    private val repository: MyBusinessRepository
) : ViewModel() {

    val name = MutableStateFlow("")
    val address = MutableStateFlow("")
    val phone = MutableStateFlow("")
    val email = MutableStateFlow("")


    private val _isUpdating = MutableStateFlow<String?>(null)
    val isUpdating: StateFlow<String?> = _isUpdating

    private val _areInputsValid = MutableStateFlow(false)
    val areInputsValid: StateFlow<Boolean> = _areInputsValid

    private val _manageBusinessResult = MutableStateFlow<Resource<Business>?>(null)
    val manageBusinessResult: StateFlow<Resource<Business>?> = _manageBusinessResult

    private val _myBusinesses = MutableStateFlow<Resource<List<Business>>?>(null)
    val myBusinesses: StateFlow<Resource<List<Business>>?> = _myBusinesses

    private val _canAddBusiness = MutableStateFlow(true)
    val canAddBusiness = _canAddBusiness

    init {
        init()
    }

    private fun init() = viewModelScope.launch {
        canAddBusiness()
        getBusinesses()
    }

    fun validateInputs() {
        _areInputsValid.value =
            name.value.trim().isNotEmpty() && address.value.trim().isNotEmpty() &&
                    phone.value.trim().isNotEmpty() && email.value.trim().isNotEmpty()
        /* @Todo more specific validations */
    }

    fun addMyBusiness() = viewModelScope.launch {
        _manageBusinessResult.value = Resource.Loading
        val business = Business(name.value, address.value, phone.value, email.value)
        _manageBusinessResult.value = repository.addMyBusiness(business)
        getBusinesses()
    }

    fun updateMyBusiness() = viewModelScope.launch {
        _manageBusinessResult.value = Resource.Loading
        val business = Business(name.value, address.value, phone.value, email.value).also {
            it.id = _isUpdating.value ?: throw IllegalArgumentException("Business Id is null, you must call setUpdating() first")
        }
        _manageBusinessResult.value = repository.updateMyBusiness(business)
        getBusinesses()
    }

    fun deleteMyBusiness() = viewModelScope.launch {
        _isUpdating.value?.let {
            _manageBusinessResult.value = Resource.Loading
            repository.deleteMyBusiness(it)
            _manageBusinessResult.value = Resource.Success(Business())
        }
        getBusinesses()
    }

    fun setUpdating(business: Business?) {
        if (business != null) {
            _isUpdating.value = business.id
            name.value = business.name
            address.value = business.address
            phone.value = business.phone
            email.value = business.email
            validateInputs()
        } else {
            _isUpdating.value = null
            name.value = ""
            address.value = ""
            phone.value = ""
            email.value = ""
        }
    }

    fun resetResource() {
        _manageBusinessResult.value = null
    }

    private suspend fun getBusinesses() {
        _myBusinesses.value = Resource.Loading
        _myBusinesses.value = repository.getMyBusinesses()
    }

    private suspend fun canAddBusiness() {
        _canAddBusiness.value = repository.canAddBusiness()
    }
}