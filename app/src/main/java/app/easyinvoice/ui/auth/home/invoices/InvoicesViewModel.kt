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

package app.easyinvoice.ui.auth.home.invoices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.easyinvoice.data.Resource
import app.easyinvoice.data.home.CustomersRepository
import app.easyinvoice.data.home.InvoiceRepository
import app.easyinvoice.data.home.MyBusinessRepository
import app.easyinvoice.data.home.TaxRepository
import app.easyinvoice.data.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val businessRepository: MyBusinessRepository,
    private val customersRepository: CustomersRepository,
    private val taxRepository: TaxRepository
) : ViewModel() {

    val desc = MutableStateFlow("")
    val qty = MutableStateFlow("")
    val price = MutableStateFlow("")

    private val _businesses = MutableStateFlow<Resource<List<Business>>?>(null)
    val businesses: StateFlow<Resource<List<Business>>?> = _businesses

    private val _customers = MutableStateFlow<Resource<List<Customer>>?>(null)
    val customers: StateFlow<Resource<List<Customer>>?> = _customers

    private val _taxes = MutableStateFlow<Resource<List<Tax>>?>(null)
    val taxes: StateFlow<Resource<List<Tax>>?> = _taxes

    private val _invoices = MutableStateFlow<Resource<List<Invoice>>?>(null)
    val invoices: StateFlow<Resource<List<Invoice>>?> = _invoices

    private val _invoice = MutableStateFlow(Invoice())
    val invoice: StateFlow<Invoice> = _invoice

    private val _createInvoice = MutableStateFlow<Resource<Invoice>?>(null)
    val createInvoice: StateFlow<Resource<Invoice>?> = _createInvoice

    private val _areInputsValid = MutableStateFlow(false)
    val areInputsValid: StateFlow<Boolean> = _areInputsValid

    private val _isUpdatingInvoiceItem = MutableStateFlow(-1)
    val isUpdatingInvoiceItem: StateFlow<Int> = _isUpdatingInvoiceItem

    init {
        getInvoices()
        getBusinesses()
        getCustomers()
        getTaxes()
    }

    private fun getInvoices() = viewModelScope.launch {
        _invoices.value = Resource.Loading
        _invoices.value = invoiceRepository.getInvoices()
    }

    private fun getBusinesses() = viewModelScope.launch {
        _businesses.value = Resource.Loading
        _businesses.value = businessRepository.getMyBusinesses()
    }

    private fun getCustomers() = viewModelScope.launch {
        _customers.value = Resource.Loading
        _customers.value = customersRepository.getCustomers()
    }

    private fun getTaxes() = viewModelScope.launch {
        _taxes.value = Resource.Loading
        _taxes.value = taxRepository.getTaxes()
    }

    fun validateInputs() {
        var valid = true
        if (desc.value.isEmpty()) {
            valid = false
        }
        if (qty.value.toDoubleOrNull() == null) {
            valid = false
        }
        if (price.value.toDoubleOrNull() == null) {
            valid = false
        }
        _areInputsValid.value = valid
    }

    fun setBusiness(business: Business) {
        _invoice.value = _invoice.value.copy(business = business)
    }

    fun setCustomer(customer: Customer) {
        _invoice.value = _invoice.value.copy(customer = customer)
        Log.e("t", "t")
    }

    fun setTax(tax: Tax) {
        _invoice.value = _invoice.value.copy(tax = tax)
    }

    fun addInvoiceItem() {
        val invoiceItem = InvoiceItem(desc.value, qty.value.toDouble(), price.value.toDouble())
        val items = _invoice.value.items + invoiceItem
        _invoice.value = _invoice.value.copy(items = items)
        desc.value = ""
        qty.value = ""
        price.value = ""
        validateInputs()
    }

    fun initUpdateInvoiceItem(index: Int) {
        val currentItem = _invoice.value.items[index]
        desc.value = currentItem.desc
        qty.value = currentItem.qty.toString()
        price.value = currentItem.price.toString()
        validateInputs()
        _isUpdatingInvoiceItem.value = index
    }

    fun updateInvoiceItem() {
        val invoiceItem = InvoiceItem(desc.value, qty.value.toDouble(), price.value.toDouble())
        val updatedItems = _invoice.value.items.toMutableList().also {
            it.removeAt(_isUpdatingInvoiceItem.value)
            it.add(_isUpdatingInvoiceItem.value, invoiceItem)
        }
        _invoice.value = _invoice.value.copy(items = updatedItems)
        desc.value = ""
        qty.value = ""
        price.value = ""
        validateInputs()
        _isUpdatingInvoiceItem.value = -1
    }

    fun deleteInvoiceItem(index: Int) {
        val updatedItemList = _invoice.value.items.toMutableList().also {
            it.removeAt(index)
        }
        _invoice.value = _invoice.value.copy(items = updatedItemList)
    }

    fun createOrUpdateInvoice() = viewModelScope.launch {
        _createInvoice.value = Resource.Loading
        if (_invoice.value.id.isEmpty())
            _createInvoice.value = invoiceRepository.addInvoice(_invoice.value)
        else
            _createInvoice.value = invoiceRepository.updateInvoice(_invoice.value)
        getInvoices()
    }

    fun canCreateInvoice(): Boolean {
        val invoice = _invoice.value
        return invoice.business != null && invoice.customer != null && invoice.tax != null && invoice.items.isNotEmpty()
    }

    fun deleteInvoice(id: String) = viewModelScope.launch {
        invoiceRepository.deleteInvoice(id)
        getInvoices()
    }

    fun updatePaidState(id: String, isPaid: Boolean) = viewModelScope.launch {
        invoiceRepository.updatePaidState(id, isPaid)
        getInvoices()
    }

    fun initInvoiceUpdate(invoice: Invoice) {
        _invoice.value = invoice
    }

    fun initNewInvoice() {
        _invoice.value = Invoice()
    }

    fun setCurrentInvoice(invoice: Invoice) {
        _invoice.value = invoice
    }
}