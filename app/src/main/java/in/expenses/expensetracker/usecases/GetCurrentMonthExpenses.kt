package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.utils.getCurrentMonthTimeRange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCurrentMonthExpensesUseCase {
    suspend operator fun invoke(): Flow<Double>
}

class GetCurrentMonthExpensesUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : GetCurrentMonthExpensesUseCase {
    override suspend fun invoke(): Flow<Double> {
        val timeRange = getCurrentMonthTimeRange()
        return transactionRepo.getTotalExpenses(timeRange.start, timeRange.end)
    }
}