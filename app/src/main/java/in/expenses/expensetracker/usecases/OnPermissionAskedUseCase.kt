package `in`.expenses.expensetracker.usecases

import `in`.expenses.expensetracker.repo.TransactionRepo
import javax.inject.Inject

interface OnPermissionAskedUseCase {
    suspend operator fun invoke()
}

class OnPermissionAskedUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : OnPermissionAskedUseCase {
    override suspend fun invoke() {
        transactionRepo.permissionAsked()
    }
}
