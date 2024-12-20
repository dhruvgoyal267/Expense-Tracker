package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNTransactionUseCase {
    suspend operator fun invoke(): Flow<List<Transaction>>
}

class GetNTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): GetNTransactionUseCase {
    override suspend fun invoke(): Flow<List<Transaction>> = transactionRepo.getLastNTransaction(5)
}