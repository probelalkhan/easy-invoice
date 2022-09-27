package app.easyinvoice.data.models

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Invoice(
    val business: Business? = null,
    val customer: Customer? = null,
    val tax: Tax? = null,
    val items: List<InvoiceItem> = listOf(),
    @field:JvmField
    var isPaid: Boolean = false,
    override var id: String = ""
) : BaseModel(id) {

    val invoiceAmount: Double
        get() = totalAmount + taxAmount

    val taxAmount: Double
        get() = totalAmount * (tax?.value ?: 1.0) / 100

    val totalAmount: Double
        get() = items.sumOf { it.qty * it.price }

    fun isPaid() = isPaid
}
