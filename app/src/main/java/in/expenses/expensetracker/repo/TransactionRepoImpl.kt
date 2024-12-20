package `in`.expenses.expensetracker.repo

import `in`.expenses.expensetracker.db.TransactionDao
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.model.toEntity
import `in`.expenses.expensetracker.model.toObj
import `in`.expenses.expensetracker.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val dispatcherProvider: DispatcherProvider
) : TransactionRepo {
    override suspend fun addTransaction(transaction: Transaction) {
        withContext(dispatcherProvider.io) {
            transactionDao.addTransaction(transaction.toEntity())
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        withContext(dispatcherProvider.io) {
            transactionDao.updateTransaction(transaction.toEntity())
        }
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        withContext(dispatcherProvider.io) {
            transactionDao.deleteTransaction(transaction.toEntity())
        }
    }

    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return withContext(dispatcherProvider.io) {
            flow {
                transactionDao.getAllTransaction().onEach { list ->
                    emit(list.map { entity ->
                        entity.toObj()
                    })
                }
            }
        }
    }

    override suspend fun getLastNTransaction(count: Int): List<Transaction> {
        return withContext(dispatcherProvider.io) {
            transactionDao.getLastNTransaction(count).map {
                it.toObj()
            }
//            flow {
//                transactionDao.getLastNTransaction(count).onEach { list ->
//                    emit(list.map { entity ->
//                        entity.toObj()
//                    })
//                }
//            }
        }
    }
}