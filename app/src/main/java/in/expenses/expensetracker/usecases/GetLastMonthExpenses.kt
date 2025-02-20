package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.utils.getLastMonthTimeRange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetLastMonthExpensesUseCase {
    suspend operator fun invoke(): Flow<Double>
}

class GetLastMonthExpensesUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : GetLastMonthExpensesUseCase {
    override suspend fun invoke(): Flow<Double> {
        val timeRange = getLastMonthTimeRange()
        return transactionRepo.getTotalExpenses(timeRange.start, timeRange.end)
    }

}