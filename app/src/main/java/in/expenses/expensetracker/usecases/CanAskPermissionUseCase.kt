package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import javax.inject.Inject

interface CanAskPermissionUseCase {
    suspend operator fun invoke(): Boolean
}

class CanAskPermissionUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : CanAskPermissionUseCase {

    companion object {
        private const val PERMISSION_ASK_THRESHOLD = 3
    }

    override suspend fun invoke(): Boolean =
        transactionRepo.permissionAskedCount() <= PERMISSION_ASK_THRESHOLD
}
