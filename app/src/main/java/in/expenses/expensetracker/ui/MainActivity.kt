package `in`.expenses.expensetracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.AndroidEntryPoint
import `in`.expenses.expensetracker.ui.composables.AddCustomTransactionBottomSheet
import `in`.expenses.expensetracker.ui.composables.HomeScreenUi
import `in`.expenses.expensetracker.ui.composables.SMSPermissionBottomSheetUI
import `in`.expenses.expensetracker.ui.theme.ExpenseTrackerTheme

@AndroidEntryPoint
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
                    val context = LocalContext.current
                    LaunchedEffect(key1 = null) {
                        viewModel.checkForSmsPermission(context)
                    }

                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions(),
                        onResult = {
                            viewModel.receivedSMSPermission(it)
                        }
                    )

                    val showSmsBottomSheet by viewModel.showSmsPermission.observeAsState(false)
                    val showAddTransactionBottomSheet by viewModel.showCustomTransaction.observeAsState(
                        false
                    )
                    val askForPermission by viewModel.requestSmsPermission.observeAsState(initial = false)

                    LaunchedEffect(key1 = askForPermission){
                        if(askForPermission){
                            permissionLauncher.launch(viewModel.getRequiredPermissions())
                        }
                    }

                    HomeScreenUi(viewModel)

                    val smsPermissionState = rememberModalBottomSheetState()

                    LaunchedEffect(key1 = showSmsBottomSheet) {
                        if (showSmsBottomSheet) {
                            smsPermissionState.show()
                        } else {
                            smsPermissionState.hide()
                        }
                    }

                    if(showSmsBottomSheet) {
                        SMSPermissionBottomSheetUI(viewModel, smsPermissionState)
                    }

                    val customTransactionState =
                        rememberModalBottomSheetState(skipPartiallyExpanded = true)

                    LaunchedEffect(key1 = showAddTransactionBottomSheet) {
                        if (showAddTransactionBottomSheet) {
                            customTransactionState.show()
                        } else {
                            customTransactionState.hide()
                        }
                    }

                    if(showAddTransactionBottomSheet) {
                        AddCustomTransactionBottomSheet(viewModel, customTransactionState)
                    }
                }
            }
        }
    }
}