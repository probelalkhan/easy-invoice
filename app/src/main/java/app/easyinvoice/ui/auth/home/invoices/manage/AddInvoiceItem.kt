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

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import app.easyinvoice.R
import app.easyinvoice.data.Resource
import app.easyinvoice.data.models.InvoiceItem
import app.easyinvoice.ui.AppScreen
import app.easyinvoice.ui.commons.FullScreenProgressbar
import app.easyinvoice.ui.faker.FakeViewModelProvider
import app.easyinvoice.ui.theme.AppTheme
import app.easyinvoice.ui.theme.spacing
import app.easyinvoice.ui.utils.toast

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddInvoiceItem(viewModel: InvoicesViewModel, navController: NavController) {
    val context = LocalContext.current
    val spacing = MaterialTheme.spacing
    val invoice = viewModel.invoice.collectAsState()
    val createInvoice = viewModel.createInvoice.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (viewModel.canCreateInvoice()) {
                        viewModel.createOrUpdateInvoice()
                    } else {
                        context.toast(R.string.create_invoice_error)
                    }
                },
            ) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.empty))
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.medium)
            ) {
                Text(
                    text = stringResource(id = R.string.add_invoice_items),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = spacing.medium)
                )

                InvoiceItemInput(viewModel)

                Divider()

                Text(
                    text = stringResource(id = R.string.invoice),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = spacing.extraSmall, bottom = spacing.extraSmall)
                )

                Divider(modifier = Modifier.padding(bottom = spacing.medium))

                LazyColumn {
                    itemsIndexed(invoice.value.items) { index, item ->
                        InvoiceItemCard(viewModel, index, item)
                    }
                }
            }

            createInvoice.value?.let {
                when (it) {
                    is Resource.Failure -> {
                        context.toast(it.exception.message!!)
                    }
                    Resource.Loading -> {
                        FullScreenProgressbar()
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(AppScreen.Invoices.route) {
                                popUpTo(AppScreen.Invoices.ManageInvoice.route) { inclusive = true }
                            }
                            context.toast(R.string.invoice_created)
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceItemInput(viewModel: InvoicesViewModel) {
    val focusManager = LocalFocusManager.current
    val spacing = MaterialTheme.spacing

    val desc = viewModel.desc.collectAsState()
    val qty = viewModel.qty.collectAsState()
    val price = viewModel.price.collectAsState()
    val areInputsValid = viewModel.areInputsValid.collectAsState()
    val isUpdatingInvoiceItem = viewModel.isUpdatingInvoiceItem.collectAsState()

    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
        Row {
            TextField(
                value = desc.value,
                onValueChange = {
                    viewModel.desc.value = it
                },
                label = { Text(text = stringResource(id = R.string.desc)) },
                modifier = Modifier
                    .weight(2f)
                    .padding(end = spacing.small),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                        viewModel.validateInputs()
                    },
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSurface),
                isError = desc.value.isEmpty()
            )

            TextField(
                value = qty.value,
                onValueChange = {
                    viewModel.qty.value = it
                },
                label = { Text(text = stringResource(id = R.string.qty)) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                        viewModel.validateInputs()
                    },
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSurface),
                isError = qty.value.toDoubleOrNull() == null
            )

            TextField(
                value = price.value,
                onValueChange = {
                    viewModel.price.value = it
                },
                label = { Text(text = stringResource(id = R.string.price)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = spacing.small),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        viewModel.validateInputs()
                    },
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSurface),
                isError = price.value.toDoubleOrNull() == null
            )
        }
        TextButton(
            onClick = {
                if (isUpdatingInvoiceItem.value != -1) {
                    viewModel.updateInvoiceItem()
                } else {
                    viewModel.addInvoiceItem()
                }
                focusManager.clearFocus()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = spacing.medium),
            enabled = areInputsValid.value,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = if (isUpdatingInvoiceItem.value != -1) stringResource(id = R.string.update) else stringResource(id = R.string.add),
                style = MaterialTheme.typography.titleMedium,
                color = if (areInputsValid.value) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
        }
    }
}

@Composable
fun InvoiceItemCard(viewModel: InvoicesViewModel, index: Int, item: InvoiceItem) {
    val spacing = MaterialTheme.spacing
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.desc,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(0.5f)
                .padding(end = spacing.medium)
        )

        Text(
            text = item.qty.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.1f)
        )

        Text(
            text = item.price.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(0.2f)
                .padding(start = spacing.medium, end = spacing.extraSmall)
        )

        IconButton(
            onClick = {
                viewModel.initUpdateInvoiceItem(index)
            },
            modifier = Modifier.weight(0.1f)
        ) {
            Image(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
        }

        IconButton(
            onClick = {
                viewModel.deleteInvoiceItem(index)
            },
            modifier = Modifier.weight(0.1f)
        ) {
            Image(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAddInvoiceItemLight() {
    AppTheme {
        //AddInvoiceItem(FakeViewModelProvider.provideInvoicesViewModel(), rememberNavController())
        //InvoiceItemInput(FakeViewModelProvider.provideInvoicesViewModel())
        InvoiceItemCard(FakeViewModelProvider.provideInvoicesViewModel(), 1, InvoiceItem("item 1", 2.00, 10.00))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAddInvoiceItemDark() {
    AppTheme {
        //AddInvoiceItem(FakeViewModelProvider.provideInvoicesViewModel(), rememberNavController())
        //InvoiceItemInput(FakeViewModelProvider.provideInvoicesViewModel())
        InvoiceItemCard(FakeViewModelProvider.provideInvoicesViewModel(), 1, InvoiceItem("item 1", 2.00, 10.00))
    }
}