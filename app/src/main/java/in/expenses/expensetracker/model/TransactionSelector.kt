package `in`.expenses.expensetracker.model

enum class TransactionSelector(val key: String) {
    ALL_TRANSACTION("All Transaction"),
    CURRENT_MONTH("Current Month"),
    LAST_MONTH("Last Month"),
    LAST_SIX_MONTH("Last 6 Months"),
    CUSTOM("Custom");

    companion object {
        fun getTransactionSelector(key: String?): TransactionSelector {
            return values().first {
                it.key == key
            }
        }
    }
}

