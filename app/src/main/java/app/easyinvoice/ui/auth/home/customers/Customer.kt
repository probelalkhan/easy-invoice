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

package app.easyinvoice.ui.auth.home.customers

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import app.easyinvoice.R
import app.easyinvoice.data.models.Customer
import app.easyinvoice.ui.theme.AppTheme
import app.easyinvoice.ui.theme.spacing

@Composable
fun Customer(customer: Customer, onClick: () -> Unit) {
    val spacing = MaterialTheme.spacing
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = spacing.medium, end = spacing.medium, top = spacing.medium)
            .clickable { onClick.invoke() }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(spacing.medium)
        ) {
            val (refIcon, refName, refAddress, refEmail, refPhone) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier
                    .size(spacing.xLarge)
                    .constrainAs(refIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                text = customer.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(refName) {
                    start.linkTo(refIcon.end, spacing.small)
                    top.linkTo(refIcon.top)
                    end.linkTo(parent.end, spacing.medium)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = customer.getCompleteAddress(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.constrainAs(refAddress) {
                    start.linkTo(refName.start)
                    top.linkTo(refName.bottom, spacing.extraSmall)
                    end.linkTo(parent.end, spacing.medium)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CustomerPreviewLight() {
    AppTheme {
        Customer(
            Customer(
                name = "Simplified Coding",
                address = "Bangalore, India",
                email = "probelalkhan@gmail.com",
                phone = "8340154535"
            )
        ) { }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomerPreviewDark() {
    AppTheme {
        Customer(
            Customer(
                name = "Simplified Coding",
                address = "Bangalore, India",
                email = "probelalkhan@gmail.com",
                phone = "8340154535"
            )
        ) { }
    }
}
