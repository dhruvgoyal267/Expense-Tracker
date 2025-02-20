package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.utils.getCurrentMonthTimeRange
import `in`.expenses.expensetracker.utils.getLastMonthTimeRange
import `in`.expenses.expensetracker.utils.getLastSixMonthTimeRange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllTransactionUseCase {
    suspend operator fun invoke(transactionSelector: TransactionSelector): Flow<List<Transaction>>
}

class GetAllTransactionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : GetAllTransactionUseCase {
    override suspend fun invoke(transactionSelector: TransactionSelector): Flow<List<Transaction>> {
        return when (transactionSelector) {
            TransactionSelector.AllTransaction -> transactionRepo.getAllTransaction()
            TransactionSelector.CurrentMonth -> {
                val timeRange = getCurrentMonthTimeRange()
                transactionRepo.getCustomTransaction(timeRange.start, timeRange.end)
            }

            TransactionSelector.LastMonth -> {
                val timeRange = getLastMonthTimeRange()
                transactionRepo.getCustomTransaction(timeRange.start, timeRange.end)
            }

            TransactionSelector.LastSixMonth -> {
                val timeRange = getLastSixMonthTimeRange()
                transactionRepo.getCustomTransaction(timeRange.start, timeRange.end)
            }

            is TransactionSelector.Custom -> {
                transactionRepo.getCustomTransaction(transactionSelector.startTime, transactionSelector.endTime)
            }
        }
    }
}