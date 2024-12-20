package `in`.expenses.expensetracker.repo

import `in`.expenses.expensetracker.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepo {
    suspend fun addTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun getLastNTransaction(count: Int): Flow<List<Transaction>>
}