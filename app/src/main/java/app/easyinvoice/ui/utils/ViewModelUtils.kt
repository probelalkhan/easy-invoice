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

package app.easyinvoice.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> NavController.getViewModelInstance(navBackStackEntry: NavBackStackEntry, route: String): VM {
    val parentEntry = getParent(navBackStackEntry, route)
    return hiltViewModel(parentEntry)
}

@Composable
fun NavController.getParent(navBackStackEntry: NavBackStackEntry, route: String): NavBackStackEntry {
    val parentEntry = remember(navBackStackEntry) {
        this.getBackStackEntry(route)
    }
    return parentEntry
}