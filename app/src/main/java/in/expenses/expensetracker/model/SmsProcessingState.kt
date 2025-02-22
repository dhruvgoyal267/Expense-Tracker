package `in`.expenses.expensetracker.model

sealed class SmsProcessingState {
    object Default : SmsProcessingState()
    data class Processing(val processed: Int, val total: Int): SmsProcessingState()
    object Processed : SmsProcessingState()
}