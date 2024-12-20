package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

interface GetCurrentMonthExpensesUseCase {
    suspend operator fun invoke(): Flow<String>
}

class GetCurrentMonthExpensesUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): GetCurrentMonthExpensesUseCase {
    override suspend fun invoke(): Flow<String> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val monthStartTimeStamp = calendar.timeInMillis

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)

        val monthEndTimeStamp = calendar.timeInMillis

        return flow {
            transactionRepo.getTotalExpenses(monthStartTimeStamp, monthEndTimeStamp).collect {
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