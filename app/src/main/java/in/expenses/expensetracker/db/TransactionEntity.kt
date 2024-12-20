package `in`.expenses.expensetracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = -1,
    val amount: String,
    val spendOn: String,
    val date: String
)