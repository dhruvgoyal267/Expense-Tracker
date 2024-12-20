package `in`.expenses.expensetracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class TransactionEntity(
    val amount: Double,
    val spendOn: String,
    val timeStamp: Long = Calendar.getInstance().timeInMillis
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}