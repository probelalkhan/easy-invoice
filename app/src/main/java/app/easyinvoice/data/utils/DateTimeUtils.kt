package app.easyinvoice.data.utils

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

val currentDateTime: Long = Calendar.getInstance().timeInMillis

fun Context.toDateString(time: Long): String {
    return DateUtils.getRelativeTimeSpanString(
        time,
        Date().time,
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString()
}


fun Long.toDateTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
    return format.format(date)
}