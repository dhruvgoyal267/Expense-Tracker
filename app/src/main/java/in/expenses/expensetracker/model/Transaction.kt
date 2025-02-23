package `in`.expenses.expensetracker.model

import `in`.expenses.expensetracker.db.TransactionEntity
import `in`.expenses.expensetracker.utils.formatDate
import `in`.expenses.expensetracker.utils.toMillis
import java.util.Calendar

data class Transaction(
    val amount: Double,
    val spendOn: String,
    val date: String
)

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        amount = amount,
        spendOn = spendOn,
        timeStamp = date.toMillis()
    )
}

fun TransactionEntity.toObj(): Transaction {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeStamp
    return Transaction(
        amount = amount,
        spendOn = spendOn,
        date = calendar.timeInMillis.formatDate()
    )
}
