package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import javax.inject.Inject

interface AddTransactionUseCase {
    suspend operator fun invoke(amount: String, spendOn: String, timeStamp: Long = System.currentTimeMillis())
}

class AddTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): AddTransactionUseCase {
    override suspend fun invoke(amount: String, spendOn: String, timeStamp: Long) {
        transactionRepo.addTransaction(amount, spendOn, timeStamp)
    }
}