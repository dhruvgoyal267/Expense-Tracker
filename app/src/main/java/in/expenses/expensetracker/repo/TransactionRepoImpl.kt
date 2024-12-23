package `in`.expenses.expensetracker.repo

import `in`.expenses.expensetracker.db.TransactionDao
import `in`.expenses.expensetracker.db.TransactionEntity
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.model.toEntity
import `in`.expenses.expensetracker.model.toObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepo {
    override suspend fun addTransaction(amount: String, spendOn: String) {
        transactionDao.addTransaction(
            TransactionEntity(amount = amount.toDouble(), spendOn = spendOn)
        )
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }

    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return flow {
            transactionDao.getAllTransaction().collect { list ->
                emit(list.map { entity ->
                    entity.toObj()
                })
            }
        }
    }

    override suspend fun getCustomTransaction(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): Flow<List<Transaction>> {
        return flow {
            transactionDao.getCustomTransaction(startTimeStamp, endTimeStamp).collect { list ->
                emit(list.map { entity ->
                    entity.toObj()
                })
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