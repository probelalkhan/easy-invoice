package app.easyinvoice.data.models

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class InvoiceItem(
    val desc: String = "",
    val qty: Double = 0.0,
    val price: Double = 0.0
) : BaseModel() {

    val amount: Double
        get() = qty * price
}