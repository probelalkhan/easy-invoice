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

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector
import app.easyinvoice.R

sealed class InvoiceMenu(@StringRes val title: Int, val icon: ImageVector) {
    object MarkAsPaid : InvoiceMenu(R.string.mark_as_paid, Icons.Filled.Check)
    object MarkAsUnPaid : InvoiceMenu(R.string.mark_as_unpaid, Icons.Filled.Clear)
    object Edit : InvoiceMenu(R.string.edit, Icons.Filled.Edit)
    object Delete : InvoiceMenu(R.string.delete, Icons.Filled.Delete)
}