package `in`.expenses.expensetracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.Transaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _showSmsPermission: MutableLiveData<Boolean> = MutableLiveData(false)
    val showSmsPermission: LiveData<Boolean> = _showSmsPermission

    private val _showCustomTransaction: MutableLiveData<Boolean> = MutableLiveData(false)
    val showCustomTransaction: LiveData<Boolean> = _showCustomTransaction

    fun getCurrentState(): AppState {
        return AppState.TRANSACTION_FOUND
    }

    fun dismissTransactionBottomSheet(){
        _showCustomTransaction.value = false
    }

    fun addTransaction(amount:String, spendOn: String) {
        _showCustomTransaction.value = false
    }

    fun allowSMSPermission(){

    }

    fun denySMSPermission(){
        _showSmsPermission.value = false
    }

    fun checkForSmsPermission(){
        viewModelScope.launch {
            delay(1000)
            _showSmsPermission.postValue(true)
        }
    }

    fun addCustomTransaction(){
        _showCustomTransaction.value = true
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