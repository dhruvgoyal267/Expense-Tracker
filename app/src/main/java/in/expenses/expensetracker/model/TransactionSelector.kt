package `in`.expenses.expensetracker.model

sealed class TransactionSelector(val key: TransactionSelectorEnum) {
    object AllTransaction : TransactionSelector(TransactionSelectorEnum.ALL_TRANSACTION)
    object CurrentMonth : TransactionSelector(TransactionSelectorEnum.CURRENT_MONTH)
    object LastMonth : TransactionSelector(TransactionSelectorEnum.LAST_MONTH)
    object LastSixMonth : TransactionSelector(TransactionSelectorEnum.LAST_SIX_MONTH)
    data class Custom(val startTime: Long, val endTime: Long) :
        TransactionSelector(TransactionSelectorEnum.CUSTOM)


    enum class TransactionSelectorEnum(val key: String) {
        ALL_TRANSACTION("All Transaction"),
        CURRENT_MONTH("Current Month"),
        LAST_MONTH("Last Month"),
        LAST_SIX_MONTH("Last 6 Months"),
        CUSTOM("Custom");

        companion object {
            fun getTransactionSelector(key: String?): TransactionSelectorEnum {
                return values().first {
                    it.key == key
                }
            }
        }
    }

    companion object {
        fun getTransactionSelector(key: String?): TransactionSelector {
            return when (TransactionSelectorEnum.getTransactionSelector(key)) {
                TransactionSelectorEnum.ALL_TRANSACTION -> AllTransaction
                TransactionSelectorEnum.CURRENT_MONTH -> CurrentMonth
                TransactionSelectorEnum.LAST_MONTH -> LastMonth
                TransactionSelectorEnum.LAST_SIX_MONTH -> LastSixMonth
                TransactionSelectorEnum.CUSTOM -> Custom(-1, -1)
            }
        }
    }
}

