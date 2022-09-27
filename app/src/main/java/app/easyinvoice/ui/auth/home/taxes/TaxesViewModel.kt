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

package app.easyinvoice.ui.auth.home.taxes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.easyinvoice.data.Resource
import app.easyinvoice.data.home.TaxRepository
import app.easyinvoice.data.models.Tax
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxesViewModel @Inject constructor(
    private val repository: TaxRepository
) : ViewModel() {

    var desc = MutableStateFlow("")
    var value = MutableStateFlow("")

    private val _isUpdating = MutableStateFlow<String?>(null)
    val isUpdating: StateFlow<String?> = _isUpdating

    private val _areInputsValid = MutableStateFlow(false)
    val areInputsValid: StateFlow<Boolean> = _areInputsValid

    private val _manageTaxResult = MutableStateFlow<Resource<Tax>?>(null)
    val manageTaxResult: StateFlow<Resource<Tax>?> = _manageTaxResult

    private val _taxes = MutableStateFlow<Resource<List<Tax>>?>(null)
    val taxes: StateFlow<Resource<List<Tax>>?> = _taxes

    init {
        init()
    }

    private fun init() = viewModelScope.launch {
        getTaxes()
    }

    fun validateInputs() {
        val validate = desc.value.trim().isNotEmpty() && value.value.trim().isNotEmpty()
        _areInputsValid.value = validate
        /* @Todo more specific validations */
    }

    fun addTax() = viewModelScope.launch {
        _manageTaxResult.value = Resource.Loading
        val tax = Tax(desc.value, value.value.toDouble())
        _manageTaxResult.value = repository.addTax(tax)
        getTaxes()
    }

    fun updateTax() = viewModelScope.launch {
        _manageTaxResult.value = Resource.Loading
        val tax = Tax(desc.value, value.value.toDouble()).also {
            it.id = _isUpdating.value ?: throw IllegalArgumentException("Business Id is null, you must call setUpdating() first")
        }
        _manageTaxResult.value = repository.updateTax(tax)
        getTaxes()
    }

    fun deleteCustomer() = viewModelScope.launch {
        _isUpdating.value?.let {
            _manageTaxResult.value = Resource.Loading
            repository.deleteTax(it)
            _manageTaxResult.value = Resource.Success(Tax())
        }
        getTaxes()
    }

    fun setUpdating(tax: Tax?) {
        if (tax != null) {
            _isUpdating.value = tax.id
            desc.value = tax.desc
            value.value = tax.value.toString()
            validateInputs()
        } else {
            _isUpdating.value = null
            desc.value = ""
            value.value = ""
        }
    }

    fun resetResource() {
        _manageTaxResult.value = null
    }

    private suspend fun getTaxes() {
        _taxes.value = Resource.Loading
        _taxes.value = repository.getTaxes()
    }
}