package `in`.expenses.expensetracker.utils

import `in`.expenses.expensetracker.model.TimeRange
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun getCurrentMonthTimeRange(): TimeRange {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val monthStartTimeStamp = calendar.timeInMillis

    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)

    val monthEndTimeStamp = calendar.timeInMillis

    return TimeRange(monthStartTimeStamp, monthEndTimeStamp)
}
fun getLastMonthTimeRange(): TimeRange {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    calendar.set(Calendar.DAY_OF_MONTH, 1)

    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1)

    val monthEndTimeStamp = calendar.timeInMillis

    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val monthStartTimeStamp = calendar.timeInMillis

    return TimeRange(monthStartTimeStamp, monthEndTimeStamp)
}
fun getLastSixMonthTimeRange(): TimeRange {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    val month = calendar.get(Calendar.MONTH)
    val year = calendar.get(Calendar.YEAR)

    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 6)

    val monthStartTimeStamp = calendar.timeInMillis

    calendar.set(Calendar.MONTH, month + 1)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.YEAR, year)
    val monthEndTimeStamp = calendar.timeInMillis

    return TimeRange(monthStartTimeStamp, monthEndTimeStamp)
}

fun formatAmount(amount: Double): String {
    return when {
        amount >= 1_00_00_000 -> String.format("₹%.1fCr", amount / 1_00_00_000.0)
        amount >= 1_00_000 -> String.format("₹%.1fL", amount / 1_00_000.0)
        amount >= 1_000 -> String.format("₹%.1fK", amount / 1_000.0)
        else -> "₹$amount"
    }.replace(".0", "")
}

fun checkForEnableBtn(amount: String, spendOn: String): Boolean {
    return amount.isNotBlank() && spendOn.isNotBlank()
}


private const val FORMAT = "dd/MM/yyyy, hh:mm a"

fun Long.formatDate(): String {
    val sdf = SimpleDateFormat(FORMAT, Locale.getDefault())
    return sdf.format(Date(this))
}

fun String.toMillis(): Long {
    val sdf = SimpleDateFormat(FORMAT, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.parse(this)?.time ?: 0L
}