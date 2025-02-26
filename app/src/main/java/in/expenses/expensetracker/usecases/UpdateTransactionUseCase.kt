package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateTransactionUseCase {
    suspend operator fun invoke(oldTransaction: Transaction, newTransaction: Transaction)
}

class UpdateTransactionUseCaseImpl @Inject constructor(
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
): UpdateTransactionUseCase {
    override suspend fun invoke(oldTransaction: Transaction, newTransaction: Transaction) {
        deleteTransactionUseCase(oldTransaction)
        addTransactionUseCase(newTransaction)
    }
}