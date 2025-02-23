package `in`.expenses.expensetracker.usecases

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.expenses.expensetracker.model.ProcessingState
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.utils.DispatcherProvider
import `in`.expenses.expensetracker.utils.formatDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.inject.Inject

interface ExportTransactionUseCase {
    suspend operator fun invoke(transactions: List<Transaction>): Flow<ProcessingState>
}

class ExportTransactionUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : ExportTransactionUseCase {
    override suspend fun invoke(transactions: List<Transaction>): Flow<ProcessingState> =
        withContext(dispatcherProvider.io) {
            flow {
                if (transactions.isEmpty()) {
                    emit(ProcessingState.Processed)
                    return@flow
                }

                val fileName = "transactions_${System.currentTimeMillis().formatDate()}.csv"

                var counter = 1
                val total = transactions.size
                val csvData = StringBuilder()
                csvData.append("Amount,SpendOn,Date,Time\n")

                for (transaction in transactions) {
                    emit(ProcessingState.Processing(counter++, total))
                    csvData.append("${transaction.amount},${transaction.spendOn},${transaction.date}\n")
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveCsvFileToDownloads(context, fileName, csvData.toString())
                } else {
                    try {
                        val csvFile = File(context.getExternalFilesDir(null), fileName)
                        val writer = FileWriter(csvFile)
                        writer.append(csvData)
                        writer.flush()
                        writer.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                emit(ProcessingState.Processed)
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveCsvFileToDownloads(context: Context, fileName: String, csvData: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "text/csv")
            put(MediaStore.Downloads.IS_PENDING, 1)
        }

        val contentResolver = context.contentResolver
        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                outputStream.write(csvData.toByteArray())
            }

            // Mark the file as ready
            contentValues.clear()
            contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
            contentResolver.update(uri, contentValues, null, null)
        }
    }
}