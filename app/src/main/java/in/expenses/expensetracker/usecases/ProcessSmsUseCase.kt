package `in`.expenses.expensetracker.usecases

import android.content.Context
import android.database.Cursor
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.expenses.expensetracker.model.SmsProcessingState
import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ProcessSmsUseCase {
    suspend operator fun invoke(): Flow<SmsProcessingState>
}

class ProcessSmsUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val extractAmountFromSmsUseCase: ExtractAmountFromSmsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val transactionRepo: TransactionRepo
) : ProcessSmsUseCase {
    override suspend fun invoke(): Flow<SmsProcessingState> =
        withContext(dispatcherProvider.default) {
            flow {
                if (transactionRepo.isSmsProcessingDone()) {
                    return@flow
                }
                try {
                    val uri: Uri = Uri.parse("content://sms/inbox")
                    val projection = arrayOf("_id", "address", "date", "body")
                    val cursor: Cursor? =
                        context.contentResolver.query(uri, projection, null, null, "date DESC")

                    cursor?.use {
                        val indexBody = it.getColumnIndex("body")
                        val indexDate = it.getColumnIndex("date")
                        var counter = 1
                        while (it.moveToNext()) {
                            emit(SmsProcessingState.Processing(counter++, cursor.count))
                            val smsBody = it.getString(indexBody)
                            val timeStamp = it.getLong(indexDate)
                            extractAmountFromSmsUseCase(smsBody)?.takeIf { amount -> amount.isNotBlank() }
                                ?.let { amount ->
                                    addTransactionUseCase(amount, "Auto Processed", timeStamp)
                                }
                        }
                        emit(SmsProcessingState.Processed)
                        transactionRepo.smsProcessingDone()
                    } ?: false
                } catch (e: Exception) {
                    emit(SmsProcessingState.Processed)
                }
            }
        }
}