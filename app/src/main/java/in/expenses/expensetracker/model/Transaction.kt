package `in`.expenses.expensetracker.model

import `in`.expenses.expensetracker.db.TransactionEntity
import java.time.LocalDate

data class Transaction(
    val amount: String,
    val spendOn: String,
    val date: String = LocalDate.now().toString()
)

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(amount = amount, spendOn = spendOn, date = date)
}

fun TransactionEntity.toObj(): Transaction {
    return Transaction(amount = amount, spendOn = spendOn, date = date)
}
