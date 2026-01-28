package `in`.expenses.expensetracker.ui.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.ProcessingState
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.usecases.ProcessingUI
import `in`.expenses.expensetracker.utils.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUi(
    viewModel: MainViewModel,
    onViewMore: (transactionSelector: TransactionSelector.TransactionSelectorEnum) -> Unit
) {

    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let { selectedUri ->
                viewModel.importTransaction(selectedUri)
            }
        }
    )

    val context = LocalContext.current
    LaunchedEffect(key1 = null) {
        viewModel.checkForSmsPermission(context)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            viewModel.onReceivedSMSPermission(it)
        }
    )

    val showSmsBottomSheet by viewModel.showSmsPermission.observeAsState(false)
    val showAddTransactionBottomSheet by viewModel.showCustomTransaction.observeAsState(
        false
    )
    val askForPermission by viewModel.requestSmsPermission.observeAsState(initial = false)

    LaunchedEffect(key1 = askForPermission) {
        if (askForPermission) {
            permissionLauncher.launch(viewModel.getRequiredPermissions())
        }
    }

    val smsPermissionState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = showSmsBottomSheet) {
        if (showSmsBottomSheet) {
            smsPermissionState.show()
        } else {
            smsPermissionState.hide()
        }
    }

    if (showSmsBottomSheet) {
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

    if (showAddTransactionBottomSheet) {
        AddCustomTransactionBottomSheet(viewModel, customTransactionState)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        TitleUi()

        VerticalSpacer(height = 16)

        val smsProcessingState =
            viewModel.smsProcessingState.collectAsState(
                ProcessingState.Default
            )

        val importProcessingState =
            viewModel.importTransactionProcessingUIState.collectAsState(
                ProcessingState.Default
            )

        LaunchedEffect(key1 = smsProcessingState) {
            when (val state = smsProcessingState.value) {
                is ProcessingState.Processed -> {
                    viewModel.showToast(
                        context.getString(
                            if (state.isError.not())
                                R.string.processing_done
                            else
                                R.string.sms_processing_failed
                        )
                    )
                }

                else -> Unit
            }
        }

        LaunchedEffect(key1 = importProcessingState) {
            when (val state = importProcessingState.value) {
                is ProcessingState.Processed -> {
                    viewModel.showToast(
                        context.getString(
                            if (state.isError.not())
                                R.string.processing_done
                            else
                                R.string.import_processing_failed
                        )
                    )
                }

                else -> Unit
            }
        }

        ProcessingUI(smsProcessingState.value, R.string.sms_processed)

        ProcessingUI(importProcessingState.value, R.string.entry_processed)

        val appState by viewModel.appState.observeAsState(AppState.LOADING)

        LaunchedEffect(key1 = null) {
            viewModel.getCurrentState()
        }

        AnimatedContent(targetState = appState, label = "") { state ->
            when (state) {
                AppState.DEFAULT -> Unit

                AppState.LOADING -> Loader()

                AppState.NO_TRANSACTION_FOUND -> NoTransactionUI(viewModel = viewModel) {
                    pickFileLauncher.launch(
                        arrayOf(
                            "application/csv",
                            "text/comma-separated-values"
                        )
                    )
                }

                AppState.TRANSACTION_FOUND -> TransactionLayoutUi(
                    viewModel = viewModel,
                    onViewMore = onViewMore,
                    importFile = {
                        pickFileLauncher.launch(
                            arrayOf(
                                "application/csv",
                                "text/comma-separated-values"
                            )
                        )
                    }
                )
            }
        }
    }
}