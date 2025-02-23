package `in`.expenses.expensetracker.ui.composables

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.ProcessingState
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.usecases.ProcessingUI
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun ViewAllTransactionsUI(
    viewModel: MainViewModel,
    selectedOption: TransactionSelector,
    onBackClicked: () -> Unit
) {
    var dateRangePicker by remember {
        mutableStateOf(false)
    }
    BackHandler {
        onBackClicked()
    }

    AnimatedVisibility(visible = dateRangePicker) {
        DateRangePickerUI { start, end ->
            dateRangePicker = false
            viewModel.loadPreDefinedCustomTransaction(TransactionSelector.Custom(start, end))
        }
    }

    ViewAllTransactionUIComposable(
        viewModel = viewModel,
        selectedOption = selectedOption,
        onBackClicked = onBackClicked
    ) {
        dateRangePicker = it
    }
}

@Composable
fun ViewAllTransactionUIComposable(
    viewModel: MainViewModel,
    selectedOption: TransactionSelector,
    onBackClicked: () -> Unit,
    showDatePicker: (show: Boolean) -> Unit
) {
    val transactions by viewModel.allTransaction.observeAsState(emptyList())
    val viewALlTransactionState by viewModel.viewAllTransactionState.observeAsState(AppState.DEFAULT)
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionValue by remember { mutableStateOf(selectedOption) }
    val options = remember {
        TransactionSelector.TransactionSelectorEnum.values()
    }

    LaunchedEffect(key1 = selectedOptionValue) {
        viewModel.loadPreDefinedCustomTransaction(selectedOptionValue)
    }

    Column {
        TitleUi(
            modifier = Modifier.padding(start = 8.dp),
            isBackEnabled = true,
            onBackClicked = onBackClicked
        )

        VerticalSpacer()

        val transactionProcessingState =
            viewModel.transactionProcessingUIState.collectAsState(
                ProcessingState.Default
            )

        ProcessingUI(
            processingState = transactionProcessingState.value,
            titleSrc = R.string.entry_processed
        )

        Box(modifier = Modifier) {
            Row(modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.card_bg),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    expanded = true
                }
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = selectedOptionValue.key.key,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.title)
                )

                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
            }

            VerticalSpacer()

            DropdownMenu(modifier = Modifier
                .background(colorResource(id = R.color.card_bg))
                .fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }) {
                options.forEach {
                    DropdownMenuItem(text = {
                        Text(
                            text = it.key,
                            fontSize = 12.sp,
                            fontWeight = if (it == selectedOptionValue.key) FontWeight.W500 else FontWeight.W400,
                            color = colorResource(id = R.color.title)
                        )
                    }, onClick = {
                        selectedOptionValue = TransactionSelector.getTransactionSelector(it.key)
                        expanded = false
                        if (it == TransactionSelector.TransactionSelectorEnum.CUSTOM) {
                            showDatePicker(true)
                        } else {
                            showDatePicker(false)
                            viewModel.loadPreDefinedCustomTransaction(selectedOptionValue)
                        }
                    })
                }
            }
        }

        VerticalSpacer()

        AnimatedContent(targetState = viewALlTransactionState, label = "Content") {
            when (it) {
                AppState.DEFAULT -> Unit
                AppState.LOADING -> Loader()
                AppState.NO_TRANSACTION_FOUND -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(
                                R.string.no_transaction_found_for_selected_option
                            ),
                            fontWeight = FontWeight.W500,
                            color = colorResource(
                                id = R.color.title,
                            ),
                            fontSize = 16.sp
                        )
                    }
                }

                AppState.TRANSACTION_FOUND -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        val context = LocalContext.current
                        val permissionLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.RequestPermission(),
                            onResult = { isGranted ->
                                if (isGranted) {
                                    viewModel.exportTransactions()
                                } else {
                                    viewModel.showToast(
                                        context.getString(R.string.we_require_write_permission_to_export_the_transactions)
                                    )
                                }
                            }
                        )

                        LazyColumn(
                            modifier = Modifier
                                .background(
                                    colorResource(id = R.color.card_bg)
                                )
                                .padding(16.dp)
                        ) {
                            items(transactions) { transaction ->
                                Column {
                                    TransactionUi(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        viewModel = viewModel,
                                        transaction = transaction
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .fillMaxWidth()
                                            .background(colorResource(id = R.color.separator_bg))
                                    )
                                }
                            }
                        }

                        if (transactions.isNotEmpty()) {
                            if (transactionProcessingState.value == ProcessingState.Processed) {
                                viewModel.showToast(
                                    stringResource(
                                        R.string.the_file_has_been_successfully_exported_to_the_downloads_folder
                                    )
                                )
                            }

                            val isBtnEnabled by remember {
                                derivedStateOf {
                                    when (transactionProcessingState.value) {
                                        ProcessingState.Default,
                                        ProcessingState.Processed -> true

                                        is ProcessingState.Processing -> false
                                    }
                                }
                            }

                            FloatingActionButton(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .alpha(if (isBtnEnabled) 1f else 0.4f)
                                    .padding(bottom = 32.dp, end = 16.dp),
                                containerColor = colorResource(id = R.color.card_bg),
                                onClick = {
                                    if (isBtnEnabled.not())
                                        return@FloatingActionButton
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                                        if (ContextCompat.checkSelfPermission(
                                                context,
                                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            ) == PackageManager.PERMISSION_GRANTED
                                        ) {
                                            viewModel.exportTransactions()
                                        } else {
                                            permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        }
                                    } else {
                                        viewModel.exportTransactions()
                                    }
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_export),
                                    contentDescription = "export"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}