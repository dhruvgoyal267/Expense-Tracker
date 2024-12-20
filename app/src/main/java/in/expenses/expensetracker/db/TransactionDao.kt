package `in`.expenses.expensetracker.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("Select * from TransactionEntity")
    fun getAllTransaction(): Flow<List<TransactionEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionEntity)
    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)
    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
    @Query("Select * from TransactionEntity order by id desc limit :count")
    fun getLastNTransactionFlow(count: Int): Flow<List<TransactionEntity>>
}