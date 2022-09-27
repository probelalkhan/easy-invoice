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

package app.easyinvoice.ui.commons

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import app.easyinvoice.R
import app.easyinvoice.ui.theme.AppTheme
import app.easyinvoice.ui.theme.spacing

@Composable
fun EmptyScreen(title: String, onRefresh: () -> Unit) {
    val spacing = MaterialTheme.spacing
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.medium)
    ) {
        val (refIcon, refTitle, refDesc, refRefresh) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_empty),
            contentDescription = stringResource(id = R.string.empty),
            modifier = Modifier
                .size(160.dp)
                .constrainAs(refIcon) {
                    start.linkTo(parent.start, spacing.large)
                    end.linkTo(parent.end, spacing.large)
                    top.linkTo(parent.top, 150.dp)
                }
        )

        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(refTitle) {
                top.linkTo(refIcon.bottom, spacing.large)
                start.linkTo(parent.start, spacing.medium)
                end.linkTo(parent.end, spacing.medium)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = stringResource(id = R.string.empty_screen),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(refDesc) {
                top.linkTo(refTitle.bottom, spacing.medium)
                start.linkTo(parent.start, spacing.medium)
                end.linkTo(parent.end, spacing.medium)
                width = Dimension.fillToConstraints
            }
        )

        Button(
            onClick = {
                onRefresh.invoke()
            },
            modifier = Modifier.constrainAs(refRefresh) {
                start.linkTo(parent.start, spacing.xxLarge)
                top.linkTo(refDesc.bottom, spacing.large)
                end.linkTo(parent.end, spacing.xxLarge)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = stringResource(id = R.string.refresh),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun EmptyScreenPreviewLight() {
    AppTheme {
        EmptyScreen(title = "Oops! No customers found") { }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreviewDark() {
    AppTheme {
        EmptyScreen(title = "Oops! No customers found") { }
    }
}
