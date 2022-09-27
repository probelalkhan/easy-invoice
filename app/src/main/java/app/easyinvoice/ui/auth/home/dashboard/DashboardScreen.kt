package app.easyinvoice.ui.auth.home.dashboard

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import app.easyinvoice.R
import app.easyinvoice.data.Resource
import app.easyinvoice.data.models.Dashboard
import app.easyinvoice.ui.commons.FullScreenProgressbar
import app.easyinvoice.ui.theme.AppTheme
import app.easyinvoice.ui.theme.spacing
import app.easyinvoice.ui.utils.toast

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val context = LocalContext.current
    val dashboard = viewModel.dashboard.collectAsState()
    dashboard.value?.let {
        when (it) {
            is Resource.Failure -> {
                context.toast(it.exception.message!!)
            }
            Resource.Loading -> {
                FullScreenProgressbar()
            }
            is Resource.Success -> {
                DashboardData(it.result)
            }
        }
    }
}

@Composable
fun DashboardData(resource: Dashboard) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        DashboardCard(
            icon = R.drawable.ic_invoice,
            title = stringResource(id = R.string.total_invoices),
            value = resource.invoiceCount.toString()
        )

        DashboardCard(
            icon = R.drawable.ic_money_bag,
            title = stringResource(id = R.string.received_amount),
            value = resource.receivedAmount.toString()
        )

        DashboardCard(
            icon = R.drawable.ic_money,
            title = stringResource(id = R.string.total_amount),
            value = resource.totalAmount.toString()
        )

        DashboardCard(
            icon = R.drawable.ic_invoice,
            title = stringResource(id = R.string.pending_invoices),
            value = resource.pendingInvoices.toString()
        )

        DashboardCard(
            icon = R.drawable.ic_money,
            title = stringResource(id = R.string.pending_amount),
            value = resource.pendingAmount.toString()
        )
    }
}

@Composable
fun DashboardCard(@DrawableRes icon: Int, title: String, value: String) {
    val spacing = MaterialTheme.spacing
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (refIcon, refTitle, refValue) = createRefs()

            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier
                    .constrainAs(refIcon) {
                        top.linkTo(parent.top, spacing.medium)
                        bottom.linkTo(parent.bottom, spacing.medium)
                        start.linkTo(parent.start, spacing.medium)
                    }
                    .size(spacing.xxLarge)
            )

            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(refTitle) {
                    top.linkTo(refIcon.top)
                    start.linkTo(refIcon.end, spacing.medium)
                    end.linkTo(parent.end, spacing.medium)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = value,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.constrainAs(refValue) {
                    top.linkTo(refTitle.bottom)
                    start.linkTo(refTitle.start, spacing.medium)
                    end.linkTo(refTitle.end, spacing.medium)
                    bottom.linkTo(refIcon.bottom)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        DashboardCard(icon = R.drawable.ic_invoice, title = "Total Invoices", value = "70")
        //Dashboard(FakeViewModelProvider.provideDashboardViewModel())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    AppTheme {
        DashboardCard(icon = R.drawable.ic_invoice, title = "Total Invoices", value = "70")
        //Dashboard(FakeViewModelProvider.provideDashboardViewModel())
    }
}
