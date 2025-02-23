package `in`.expenses.expensetracker.ui

import android.content.Intent
import android.os.Bundle
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
import `in`.expenses.expensetracker.model.NavScreens
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.composables.HomeScreenUi
import `in`.expenses.expensetracker.ui.composables.ViewAllTransactionsUI
import `in`.expenses.expensetracker.ui.theme.ExpenseTrackerTheme
import `in`.expenses.expensetracker.utils.AppConstants

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkForAmount(intent)
        setContent {
            ExpenseTrackerTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                val toastMsgState = viewModel.toastMsg.collectAsState()

                LaunchedEffect(key1 = toastMsgState.value) {
                    toastMsgState.value.takeIf { it.isNotBlank() }?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .background(
                                color = colorResource(id = R.color.app_bg)
                            )
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = NavScreens.HOME.name
                        ) {
                            composable(NavScreens.HOME.name) {
                                HomeScreenUi(viewModel = viewModel) {
                                    navController.navigate("${NavScreens.VIEW_ALL_TRANSACTION.name}/${it.key}")
                                }
                            }
                            composable("${NavScreens.VIEW_ALL_TRANSACTION.name}/{selectedOption}") { backStackEntry ->
                                val selectedOption =
                                    backStackEntry.arguments?.getString("selectedOption")
                                ViewAllTransactionsUI(
                                    viewModel = viewModel,
                                    selectedOption = TransactionSelector.getTransactionSelector(
                                        selectedOption
                                    )
                                ) {
                                    viewModel.stopListeningAllTransaction()
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
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