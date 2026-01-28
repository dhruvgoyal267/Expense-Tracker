package `in`.expenses.expensetracker.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.broadcast.SmsBroadCastReceiver
import `in`.expenses.expensetracker.model.NavScreens
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.composables.AppRoot
import `in`.expenses.expensetracker.ui.composables.HomeScreenUi
import `in`.expenses.expensetracker.ui.composables.ViewAllTransactionsUI
import `in`.expenses.expensetracker.ui.theme.ExpenseTrackerTheme
import `in`.expenses.expensetracker.utils.AppConstants
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkForAmount(intent)
        setContent {
            ExpenseTrackerTheme {
                AppRoot(viewModel = viewModel)
            }
        }
    }

    private fun checkForAmount(intent: Intent) {
        val amount = intent.getStringExtra(AppConstants.AMOUNT)
        amount?.let {
            intent.removeExtra(AppConstants.AMOUNT)
            viewModel.preFetchedAmount = it
            viewModel.addCustomTransaction()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        checkForAmount(intent)
    }
}