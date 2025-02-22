package `in`.expenses.expensetracker.ui

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.SmsProcessingState
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.usecases.AddTransactionUseCase
import `in`.expenses.expensetracker.usecases.CanAskPermissionUseCase
import `in`.expenses.expensetracker.usecases.DeleteTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetAllTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetCurrentMonthExpensesUseCase
import `in`.expenses.expensetracker.usecases.GetLastMonthExpensesUseCase
import `in`.expenses.expensetracker.usecases.GetNTransactionUseCase
import `in`.expenses.expensetracker.usecases.OnPermissionAskedUseCase
import `in`.expenses.expensetracker.usecases.ProcessSmsUseCase
import `in`.expenses.expensetracker.usecases.UpdateTransactionUseCase
import `in`.expenses.expensetracker.utils.DispatcherProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getAllTransactionUseCase: GetAllTransactionUseCase,
    private val getNTransactionUseCase: GetNTransactionUseCase,
    private val getCurrentMonthExpensesUseCase: GetCurrentMonthExpensesUseCase,
    private val getLastMonthExpensesUseCase: GetLastMonthExpensesUseCase,
    private val onPermissionAskedUseCase: OnPermissionAskedUseCase,
    private val canAskPermissionUseCase: CanAskPermissionUseCase,
    private val processSmsUseCase: ProcessSmsUseCase
) : ViewModel() {

    private val _showSmsPermission: MutableLiveData<Boolean> = MutableLiveData(false)
    val showSmsPermission: LiveData<Boolean> = _showSmsPermission

    private val _requestSmsPermission: MutableLiveData<Boolean> = MutableLiveData(false)
    val requestSmsPermission: LiveData<Boolean> = _requestSmsPermission

    private val _showCustomTransaction: MutableLiveData<Boolean> = MutableLiveData(false)
    val showCustomTransaction: LiveData<Boolean> = _showCustomTransaction

    private val _appState: MutableLiveData<AppState> = MutableLiveData(AppState.LOADING)
    val appState: LiveData<AppState> = _appState

    private val _viewAllTransactionState: MutableLiveData<AppState> =
        MutableLiveData(AppState.LOADING)
    val viewAllTransactionState: LiveData<AppState> = _viewAllTransactionState

    private val _recentTransactions: MutableLiveData<List<Transaction>> = MutableLiveData()
    val recentTransaction: LiveData<List<Transaction>> = _recentTransactions

    private val _allTransactions: MutableLiveData<List<Transaction>> = MutableLiveData()
    val allTransaction: LiveData<List<Transaction>> = _allTransactions

    private val _currentMonthExpense: MutableLiveData<Double> = MutableLiveData()
    val currentMonthExpense: LiveData<Double> = _currentMonthExpense

    private val _lastMonthExpense: MutableLiveData<Double> = MutableLiveData()
    val lastMonthExpense: LiveData<Double> = _lastMonthExpense

    private val _shouldShowAskSmsPermissionNudge: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowAskSmsPermissionNudge: LiveData<Boolean> = _shouldShowAskSmsPermissionNudge

    private val _smsProcessingUIState: MutableStateFlow<SmsProcessingState> =
        MutableStateFlow(SmsProcessingState.Default)
    val smsProcessingState: StateFlow<SmsProcessingState> = _smsProcessingUIState

    private var isSmsProcessingInProgress = false
    private var allTransactionJob: Job? = null

    var preFetchedAmount: String = ""

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
        preFetchedAmount = ""
        _showCustomTransaction.value = false
    }

    fun addTransaction(amount: String, spendOn: String) {
        _showCustomTransaction.value = false
        viewModelScope.launch {
            addTransactionUseCase(amount, spendOn)
        }
    }

    fun onReceivedSMSPermission(result: Map<String, Boolean>) {
        val isPermissionGranted = result.all { it.value }
        if (isPermissionGranted) {
            processSms()
        }
        _shouldShowAskSmsPermissionNudge.postValue(isPermissionGranted.not())
    }

    private fun processSms() {
        if (isSmsProcessingInProgress)
            return
        isSmsProcessingInProgress = true
        viewModelScope.launch {
            processSmsUseCase().collect {
                _smsProcessingUIState.value = it
                if (it is SmsProcessingState.Processed) {
                    isSmsProcessingInProgress = false
                }
            }
        }
    }

    fun denySMSPermission() {
        _showSmsPermission.value = false
    }

    fun checkForSmsPermission(context: Context) {
        viewModelScope.launch(dispatcherProvider.default) {
            val hasPermission = hasSmsPermission(context)
            val canAskPermission = canAskPermissionUseCase()
            when {
                hasPermission.not() && canAskPermission -> {
                    delay(1000)
                    _shouldShowAskSmsPermissionNudge.postValue(false)
                    _showSmsPermission.postValue(true)
                    onPermissionAskedUseCase()
                }

                canAskPermission.not() -> {
                    _shouldShowAskSmsPermissionNudge.postValue(
                        shouldShowRequestPermissionRationale(
                            context
                        )
                    )
                }

                hasPermission -> {
                    processSms()
                }
            }
        }
    }

    private fun shouldShowRequestPermissionRationale(context: Context): Boolean {
        return (context as? Activity)?.let { activity ->
            requiredPermissions.all {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    it
                )
            }
        } ?: false
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

    fun loadPreDefinedCustomTransaction(transactionSelector: TransactionSelector) {
        allTransactionJob?.cancel()
        _viewAllTransactionState.postValue(AppState.LOADING)
        allTransactionJob = viewModelScope.launch {
            getAllTransactionUseCase(transactionSelector).collect {
                val state =
                    if (it.isEmpty()) AppState.NO_TRANSACTION_FOUND else AppState.TRANSACTION_FOUND
                _viewAllTransactionState.postValue(state)
                _allTransactions.postValue(it)
            }
        }
    }

    fun stopListeningAllTransaction() {
        allTransactionJob?.cancel()
        allTransactionJob = null
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            updateTransactionUseCase(transaction = transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            deleteTransactionUseCase(transaction)
        }
    }

    fun loadExpenses() {
        viewModelScope.launch {
            getCurrentMonthExpensesUseCase().collect {
                _currentMonthExpense.postValue(it)
            }

            getLastMonthExpensesUseCase().collect {
                _lastMonthExpense.postValue(it)
            }
        }
    }
}