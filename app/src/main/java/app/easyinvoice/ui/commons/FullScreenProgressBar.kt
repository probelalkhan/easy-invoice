package app.easyinvoice.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun FullScreenProgressbar() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(Color.Transparent)
    ) {
        val progressbar = createRef()
        CircularProgressIndicator(
            modifier = Modifier.constrainAs(progressbar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        )
    }
}