package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllTransactionUseCase {
    suspend operator fun invoke(): Flow<List<Transaction>>
}

class GetAllTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): GetAllTransactionUseCase {
    override suspend fun invoke(): Flow<List<Transaction>> = transactionRepo.getAllTransaction()
}