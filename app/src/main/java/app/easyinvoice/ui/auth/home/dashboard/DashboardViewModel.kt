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

package app.easyinvoice.ui.auth.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.easyinvoice.data.Resource
import app.easyinvoice.data.home.DashboardRepository
import app.easyinvoice.data.models.Dashboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _dashboard = MutableStateFlow<Resource<Dashboard>?>(null)
    val dashboard: StateFlow<Resource<Dashboard>?> = _dashboard

    init {
        getDashboardInfo()
    }

    private fun getDashboardInfo() = viewModelScope.launch {
        _dashboard.value = Resource.Loading
        _dashboard.value = repository.getDashboardInfo()
    }
}