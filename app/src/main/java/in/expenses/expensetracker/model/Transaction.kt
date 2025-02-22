package `in`.expenses.expensetracker.model

import `in`.expenses.expensetracker.db.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Transaction(
    val amount: Double,
    val spendOn: String,
    val date: String
)

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        amount = amount,
        spendOn = spendOn,
        timeStamp = Calendar.getInstance().timeInMillis
    )
}

fun TransactionEntity.toObj(): Transaction {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeStamp
    val sdf = SimpleDateFormat("dd/MM/yyyy, hh:mm a", Locale.ENGLISH)
    return Transaction(
        amount = amount,
        spendOn = spendOn,
        date = sdf.format(calendar.time)
    )
}
