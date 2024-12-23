package `in`.expenses.expensetracker.ui.composables

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun ViewAllTransactionsUI(
    viewModel: MainViewModel,
    selectedOption: TransactionSelector,
    onBackClicked: () -> Unit
) {
    val transactions by viewModel.allTransaction.observeAsState(emptyList())
    val viewALlTransactionState by viewModel.viewAllTransactionState.observeAsState(AppState.LOADING)
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionValue by remember { mutableStateOf(selectedOption) }
    val options = remember {
        TransactionSelector.values()
    }

    LaunchedEffect(key1 = selectedOptionValue) {
        viewModel.loadPreDefinedCustomTransaction(selectedOptionValue)
    }

    BackHandler {
        onBackClicked()
    }

    Column(modifier = Modifier.background(colorResource(id = R.color.app_bg))) {
        TitleUi(
            modifier = Modifier.padding(start = 8.dp),
            isBackEnabled = true,
            onBackClicked = onBackClicked
        )

        VerticalSpacer()

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
                    text = selectedOptionValue.key,
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
                            fontWeight = if (it == selectedOptionValue) FontWeight.W500 else FontWeight.W400,
                            color = colorResource(id = R.color.title)
                        )
                    }, onClick = {
                        selectedOptionValue = it
                        expanded = false
                        if (it == TransactionSelector.CUSTOM) {

                        } else {
                            viewModel.loadPreDefinedCustomTransaction(selectedOptionValue)
                        }
                    })
                }
            }
        }

        VerticalSpacer()

        AnimatedContent(targetState = viewALlTransactionState, label = "Content") {
            when(it) {
                AppState.LOADING -> Loader()
                AppState.NO_TRANSACTION_FOUND -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No transaction found for selected option",
                            fontWeight = FontWeight.W500,
                            color = colorResource(
                                id = R.color.title,
                            ),
                            fontSize = 16.sp
                        )
                    }
                }
                AppState.TRANSACTION_FOUND -> {
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
                }
            }
        }
    }
}