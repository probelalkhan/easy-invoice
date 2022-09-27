package app.easyinvoice.ui.auth.home.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.easyinvoice.data.Resource
import app.easyinvoice.data.home.CustomersRepository
import app.easyinvoice.data.models.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val repository: CustomersRepository
) : ViewModel() {

    var name = MutableStateFlow("")
    var address = MutableStateFlow("")
    var phone = MutableStateFlow("")
    var email = MutableStateFlow("")

    private val _isUpdating = MutableStateFlow<String?>(null)
    val isUpdating: StateFlow<String?> = _isUpdating

    private val _areInputsValid = MutableStateFlow(false)
    val areInputsValid: StateFlow<Boolean> = _areInputsValid

    private val _manageCustomerResult = MutableStateFlow<Resource<Customer>?>(null)
    val manageCustomerResult: StateFlow<Resource<Customer>?> = _manageCustomerResult

    private val _customers = MutableStateFlow<Resource<List<Customer>>?>(null)
    val customers: StateFlow<Resource<List<Customer>>?> = _customers

    init {
        init()
    }

    private fun init() = viewModelScope.launch {
        getCustomers()
    }

    fun validateInputs() {
        _areInputsValid.value =
            name.value.trim().isNotEmpty() && address.value.trim().isNotEmpty() &&
                    phone.value.trim().isNotEmpty() && email.value.trim().isNotEmpty()
        /* @Todo more specific validations */
    }

    fun addCustomer() = viewModelScope.launch {
        _manageCustomerResult.value = Resource.Loading
        val customer = Customer(name.value, address.value, phone.value, email.value)
        _manageCustomerResult.value = repository.addCustomer(customer)
        getCustomers()
    }

    fun updateCustomer() = viewModelScope.launch {
        _manageCustomerResult.value = Resource.Loading
        val customer = Customer(name.value, address.value, phone.value, email.value).also {
            it.id = _isUpdating.value ?: throw IllegalArgumentException("Business Id is null, you must call setUpdating() first")
        }
        _manageCustomerResult.value = repository.updateCustomer(customer)
        getCustomers()
    }

    fun deleteCustomer() = viewModelScope.launch {
        _isUpdating.value?.let {
            _manageCustomerResult.value = Resource.Loading
            repository.deleteCustomer(it)
            _manageCustomerResult.value = Resource.Success(Customer())
        }
        getCustomers()
    }

    fun setUpdating(customer: Customer?) {
        if (customer != null) {
            _isUpdating.value = customer.id
            name.value = customer.name
            address.value = customer.address
            phone.value = customer.phone
            email.value = customer.email
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
        _manageCustomerResult.value = null
    }

    private suspend fun getCustomers() {
        _customers.value = Resource.Loading
        _customers.value = repository.getCustomers()
    }
}