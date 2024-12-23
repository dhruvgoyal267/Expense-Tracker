package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.utils.getCurrentMonthTimeRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetCurrentMonthExpensesUseCase {
    suspend operator fun invoke(): Flow<String>
}

class GetCurrentMonthExpensesUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): GetCurrentMonthExpensesUseCase {
    override suspend fun invoke(): Flow<String> {
        val timeRange = getCurrentMonthTimeRange()
        return flow {
            transactionRepo.getTotalExpenses(timeRange.start, timeRange.end).collect {
                val expense = if(it == 0.0){
                    "NA"
                }else {
                    "₹$it"
                }
                emit(expense)
            }
        }
    }
}