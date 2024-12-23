package `in`.expenses.expensetracker.ui.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUi(
    viewModel: MainViewModel,
    onViewMore: (transactionSelector: TransactionSelector) -> Unit
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

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.app_bg))
            .padding(horizontal = 16.dp)
            .scrollable(scrollState, orientation = Orientation.Vertical),
    ) {
        TitleUi()

        VerticalSpacer(height = 16)

        val appState by viewModel.appState.observeAsState(AppState.LOADING)

        LaunchedEffect(key1 = null) {
            viewModel.getCurrentState()
        }

        AnimatedContent(targetState = appState, label = "") { state ->
            when (state) {
                AppState.LOADING -> Loader()

                AppState.NO_TRANSACTION_FOUND -> NoTransactionUI(viewModel = viewModel)

                AppState.TRANSACTION_FOUND -> TransactionLayoutUi(
                    viewModel = viewModel,
                    onViewMore = onViewMore
                )
            }
        }
    }
}