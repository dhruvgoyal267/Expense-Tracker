package `in`.expenses.expensetracker.model

sealed class ProcessingState {
    object Default : ProcessingState()
    data class Processing(val processed: Int, val total: Int): ProcessingState()
    object Processed : ProcessingState()
}