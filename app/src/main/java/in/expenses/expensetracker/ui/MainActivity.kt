package `in`.expenses.expensetracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import `in`.expenses.expensetracker.ui.composables.AddCustomTransactionBottomSheet
import `in`.expenses.expensetracker.ui.composables.HomeScreenUi
import `in`.expenses.expensetracker.ui.composables.SMSPermissionBottomSheetUI
import `in`.expenses.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(key1 = null) {
                        viewModel.checkForSmsPermission()
                    }

                    val showSmsBottomSheet by viewModel.showSmsPermission.observeAsState(false)
                    val showAddTransactionBottomSheet by viewModel.showCustomTransaction.observeAsState(
                        false
                    )

                    HomeScreenUi(viewModel)

                    val smsPermissionState = rememberModalBottomSheetState()

                    LaunchedEffect(key1 = showSmsBottomSheet) {
                        if (showSmsBottomSheet) {
                            smsPermissionState.show()
                        } else {
                            smsPermissionState.hide()
                        }
                    }

                    SMSPermissionBottomSheetUI(viewModel, smsPermissionState)

                    val customTransactionState =
                        rememberModalBottomSheetState(skipPartiallyExpanded = true)

                    LaunchedEffect(key1 = showAddTransactionBottomSheet) {
                        if (showAddTransactionBottomSheet) {
                            customTransactionState.show()
                        } else {
                            customTransactionState.hide()
                        }
                    }
                    AddCustomTransactionBottomSheet(viewModel, customTransactionState)
                }
            }
        }
    }
}