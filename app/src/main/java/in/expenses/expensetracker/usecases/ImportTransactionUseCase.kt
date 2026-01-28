package `in`.expenses.expensetracker.usecases

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.expenses.expensetracker.model.ProcessingState
import `in`.expenses.expensetracker.utils.DispatcherProvider
import `in`.expenses.expensetracker.utils.toMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ImportTransactionUseCase {
    suspend operator fun invoke(uri: Uri): Flow<ProcessingState>
}

class ImportTransactionUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val addTransactionUseCase: AddTransactionUseCase
) : ImportTransactionUseCase {
    override suspend fun invoke(uri: Uri): Flow<ProcessingState> =
        withContext(dispatcherProvider.io) {
            flow {
                try {
                    val content = readContentFromUri(context, uri)
                    val size = content.size
                    content.forEachIndexed { index, s ->
                        if (index > 0) {
                            emit(ProcessingState.Processing(index, size-1))
                            val data = s.split(",")
                            val amount = data[0].replace(",", "").replace("₹", "")
                            val date = "${data[2]}, ${data[3]}"
                            if (amount.toDoubleOrNull() == null || date.toMillis() == 0L) {
                                throw Exception("Data format is not correct")
                            }
                            addTransactionUseCase(amount, data[1], date)
                        }
                    }
                    emit(ProcessingState.Processed(false))
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(ProcessingState.Processed(true))
                }
            }
        }

    private fun readContentFromUri(context: Context, uri: Uri): List<String> {
        val inputStream = context.contentResolver.openInputStream(uri)
        val content = inputStream?.bufferedReader().use {
            it?.readLines()
        }
        inputStream?.close()
        return content.orEmpty()
    }
}