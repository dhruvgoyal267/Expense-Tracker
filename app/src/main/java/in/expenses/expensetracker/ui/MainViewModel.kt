package `in`.expenses.expensetracker.ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.usecases.AddTransactionUseCase
import `in`.expenses.expensetracker.usecases.DeleteTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetAllTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetNTransactionUseCase
import `in`.expenses.expensetracker.usecases.UpdateTransactionUseCase
import `in`.expenses.expensetracker.utils.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getAllTransactionUseCase: GetAllTransactionUseCase,
    private val getNTransactionUseCase: GetNTransactionUseCase
) : ViewModel() {

    private val _showSmsPermission: MutableLiveData<Boolean> = MutableLiveData(false)
    val showSmsPermission: LiveData<Boolean> = _showSmsPermission

    private val _requestSmsPermission: MutableLiveData<Boolean> = MutableLiveData(false)
    val requestSmsPermission: LiveData<Boolean> = _requestSmsPermission

    private val _showCustomTransaction: MutableLiveData<Boolean> = MutableLiveData(false)
    val showCustomTransaction: LiveData<Boolean> = _showCustomTransaction

    private val _appState: MutableLiveData<AppState> = MutableLiveData(AppState.LOADING)
    val appState: LiveData<AppState> = _appState

    private val _recentTransactions: MutableLiveData<List<Transaction>> = MutableLiveData()
    val recentTransaction: LiveData<List<Transaction>> = _recentTransactions

    private val requiredPermissions = arrayOf(
        android.Manifest.permission.READ_SMS,
        android.Manifest.permission.RECEIVE_SMS
    )

    fun getRequiredPermissions(): Array<String> = requiredPermissions

    fun getCurrentState() {
        viewModelScope.launch {
            getNTransactionUseCase().collect { transactions ->
                val appState = if (transactions.isEmpty()) {
                    AppState.NO_TRANSACTION_FOUND
                } else {
                    _recentTransactions.postValue(transactions)
                    AppState.TRANSACTION_FOUND
                }
                _appState.postValue(appState)
            }
        }
    }

    fun dismissTransactionBottomSheet() {
        _showCustomTransaction.value = false
    }

    fun addTransaction(amount: String, spendOn: String) {
        _showCustomTransaction.value = false
        viewModelScope.launch {
            addTransactionUseCase(Transaction(amount, spendOn))
        }
    }

    fun receivedSMSPermission(result: Map<String, Boolean>) {
    }

    fun denySMSPermission() {
        _showSmsPermission.value = false
    }

    fun checkForSmsPermission(context: Context) {
        viewModelScope.launch(dispatcherProvider.default) {
            val hasPermission = hasSmsPermission(context)
            if (hasPermission.not()) {
                delay(1000)
                _showSmsPermission.postValue(true)
            }
        }
    }

    private fun hasSmsPermission(context: Context): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun askForSmsPermission() {
        _showSmsPermission.value = false
        _requestSmsPermission.value = true
    }

    fun addCustomTransaction() {
        _showCustomTransaction.value = true
    }

    fun viewMoreTransactionClicked() {}

    fun getCurrentMonthExpenses(): String {
        return "127231"
    }

    fun getLastMonthExpenses(): String {
        return "117231"
    }

}