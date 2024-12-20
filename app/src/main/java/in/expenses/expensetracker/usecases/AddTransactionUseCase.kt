package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AddTransactionUseCase {
    suspend operator fun invoke(transaction: Transaction)
}

class AddTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): AddTransactionUseCase {
    override suspend fun invoke(transaction: Transaction) {
        transactionRepo.addTransaction(transaction)
    }
}