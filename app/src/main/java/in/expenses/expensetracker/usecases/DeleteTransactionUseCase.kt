package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DeleteTransactionUseCase {
    suspend operator fun invoke(transaction: Transaction)
}

class DeleteTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): DeleteTransactionUseCase {
    override suspend fun invoke(transaction: Transaction) {
        transactionRepo.deleteTransaction(transaction)
    }
}