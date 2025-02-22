package `in`.expenses.expensetracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    val amount: Double,
    val spendOn: String,
    @PrimaryKey val timeStamp: Long
)