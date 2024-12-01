package `in`.expenses.expensetracker.ui

import androidx.lifecycle.ViewModel
import `in`.expenses.expensetracker.model.Transaction

class MainViewModel : ViewModel() {
    fun getRecentTransaction(): List<Transaction>{
        return listOf(
            Transaction("1781", "Life Insurance", "21/12/2024"),
            Transaction("7812", "HDFC Credit card emi", "22/12/2024"),
            Transaction("200", "Saloon", "23/12/2024")
        )
    }

    fun viewMoreTransactionClicked() {}

}