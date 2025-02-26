package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.repo.TransactionRepo
import javax.inject.Inject

interface AddTransactionUseCase {
    suspend operator fun invoke(amount: String, spendOn: String, timeStamp: String)
    suspend operator fun invoke(transaction: Transaction)
}

class AddTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : AddTransactionUseCase {
    override suspend fun invoke(amount: String, spendOn: String, timeStamp: String) {
        transactionRepo.addTransaction(Transaction(amount, spendOn, timeStamp))
    }

    override suspend fun invoke(transaction: Transaction) {
        transactionRepo.addTransaction(transaction)
    }
}