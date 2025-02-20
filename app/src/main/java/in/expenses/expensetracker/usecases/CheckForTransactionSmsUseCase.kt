package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.utils.AppConstants
import javax.inject.Inject
import javax.inject.Named

interface CheckForTransactionSmsUseCase {
    operator fun invoke(smsBody: String): Boolean
}

class CheckForTransactionSmsUseCaseImpl @Inject constructor(
    @Named(AppConstants.BANKS) private val banks: List<String>,
    @Named(AppConstants.DEBIT_TRANSACTION_TYPE) private val debitTransactionType: List<String>,
): CheckForTransactionSmsUseCase {
    override fun invoke(smsBody: String): Boolean {
        return banks.any {
            smsBody.contains(
                it,
                true
            )
        } && debitTransactionType.any { smsBody.contains(it, true) }
    }
}