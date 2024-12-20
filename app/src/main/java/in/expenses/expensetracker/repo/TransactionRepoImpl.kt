package `in`.expenses.expensetracker.repo

import `in`.expenses.expensetracker.db.TransactionDao
import `in`.expenses.expensetracker.db.TransactionEntity
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.model.toEntity
import `in`.expenses.expensetracker.model.toObj
import `in`.expenses.expensetracker.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val dispatcherProvider: DispatcherProvider
) : TransactionRepo {
    override suspend fun addTransaction(amount: String, spendOn: String) {
        transactionDao.addTransaction(
            TransactionEntity(amount= amount.toDouble(), spendOn = spendOn)
        )
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }

    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return withContext(dispatcherProvider.io) {
            flow {
                transactionDao.getAllTransaction().collect { list ->
                    emit(list.map { entity ->
                        entity.toObj()
                    })
                }
            }
        }
    }

    override suspend fun getLastNTransaction(count: Int): Flow<List<Transaction>> {
        return flow {
            transactionDao.getLastNTransactionFlow(count).collect { list ->
                emit(list.map { entity ->
                    entity.toObj()
                })
            }
        }
    }

    override suspend fun getTotalExpenses(startTimeStamp: Long, endTimeStamp: Long): Flow<Double> {
        return transactionDao.getTotalExpense(startTimeStamp, endTimeStamp)
    }
}