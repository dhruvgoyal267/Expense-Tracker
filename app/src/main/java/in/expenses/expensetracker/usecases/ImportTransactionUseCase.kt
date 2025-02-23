package `in`.expenses.expensetracker.usecases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.expenses.expensetracker.model.ProcessingState
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ImportTransactionUseCase {
    suspend operator fun invoke(transactions: List<Transaction>): Flow<ProcessingState>
}

class ImportTransactionUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : ExportTransactionUseCase {
    override suspend fun invoke(transactions: List<Transaction>): Flow<ProcessingState> = flow { }
}