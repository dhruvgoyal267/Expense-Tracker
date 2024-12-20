package `in`.expenses.expensetracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    val amount: String,
    val spendOn: String,
    val date: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}