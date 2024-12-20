package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateTransactionUseCase {
    suspend operator fun invoke(transaction: Transaction)
}

class UpdateTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): UpdateTransactionUseCase {
    override suspend fun invoke(transaction: Transaction) {
        transactionRepo.updateTransaction(transaction)
    }
}