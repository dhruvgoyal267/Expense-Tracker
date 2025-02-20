package `in`.expenses.expensetracker.utils

import `in`.expenses.expensetracker.model.TimeRange
import java.util.Calendar


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

    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 6)

    val monthStartTimeStamp = calendar.timeInMillis

    calendar.set(Calendar.MONTH, month + 1)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
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