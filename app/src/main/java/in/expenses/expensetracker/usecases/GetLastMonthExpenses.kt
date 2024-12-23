package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.utils.getLastMonthTimeRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetLastMonthExpensesUseCase {
    suspend operator fun invoke(): Flow<String>
}

class GetLastMonthExpensesUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : GetLastMonthExpensesUseCase {
    override suspend fun invoke(): Flow<String> {
        val timeRange = getLastMonthTimeRange()

        return flow {
            transactionRepo.getTotalExpenses(timeRange.start, timeRange.end).collect {
                val expense = if (it == 0.0) {
                    "NA"
                } else {
                    "₹$it"
                }
                emit(expense)
            }
        }
    }

}