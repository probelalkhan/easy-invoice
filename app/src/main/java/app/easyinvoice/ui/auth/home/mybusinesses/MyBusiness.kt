package app.easyinvoice.ui.auth.home.mybusinesses

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
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
import app.easyinvoice.data.models.Business
import app.easyinvoice.ui.theme.AppTheme
import app.easyinvoice.ui.theme.spacing

@Composable
fun MyBusiness(business: Business, onClick: () -> Unit) {
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
                text = business.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(refName) {
                    start.linkTo(refIcon.end, spacing.small)
                    top.linkTo(refIcon.top)
                    end.linkTo(parent.end, spacing.medium)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = business.getCompleteAddress(),
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
fun MyBusinessPreviewLight() {
    AppTheme {
        MyBusiness(
            Business(
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
fun MyBusinessPreviewDark() {
    AppTheme {
        MyBusiness(
            Business(
                name = "Simplified Coding",
                address = "Bangalore, India",
                email = "probelalkhan@gmail.com",
                phone = "8340154535"
            )
        ) { }
    }
}
