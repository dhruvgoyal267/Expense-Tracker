package `in`.expenses.expensetracker.repo

import `in`.expenses.expensetracker.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepo {
    suspend fun addTransaction(amount: String, spendOn: String)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun getLastNTransaction(count: Int): Flow<List<Transaction>>
    suspend fun getTotalExpenses(startTimeStamp: Long, endTimeStamp: Long): Flow<Double>
}