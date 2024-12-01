package `in`.expenses.expensetracker.ui

import androidx.lifecycle.ViewModel
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.Transaction

class MainViewModel : ViewModel() {

    fun getCurrentState(): AppState {
        return AppState.TRANSACTION_FOUND
    }

    fun addTransaction() {

    }

    fun allowSMSPermission(){

    }

    fun getNoTransactionFoundDescription(): String {
        return "Allow SMS permission, so that we can record transaction automatically"
    }

    fun getRecentTransaction(): List<Transaction> {
        return listOf(
            Transaction("1781", "Life Insurance", "21/12/2024"),
            Transaction("7812", "HDFC Credit card emi", "22/12/2024"),
            Transaction("200", "Saloon", "23/12/2024")
        )
    }

    fun viewMoreTransactionClicked() {}

    fun getCurrentMonthExpenses(): String {
        return "127231"
    }

    fun getLastMonthExpenses(): String {
        return "117231"
    }

}