package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.HorizontalSpacer
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun TransactionLayoutUi(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onViewMore: (transactionSelector: TransactionSelector.TransactionSelectorEnum) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        RecentTransactionUi(viewModel = viewModel, onViewMore = onViewMore)

        VerticalSpacer(height = 16)

        val currentMonthExpense by viewModel.currentMonthExpense.observeAsState(0.0)
        val lastMonthExpense by viewModel.lastMonthExpense.observeAsState(0.0)

        LaunchedEffect(key1 = viewModel) {
            viewModel.loadExpenses()
        }

        Row {
            ExpenseCardUI(
                modifier = Modifier
                    .weight(1f),
                amount = currentMonthExpense,
                description = "Current month expenses"
            ) {
                onViewMore(TransactionSelector.TransactionSelectorEnum.CURRENT_MONTH)
            }

            HorizontalSpacer()

            ExpenseCardUI(
                modifier = Modifier
                    .weight(1f),
                amount = lastMonthExpense,
                description = "Last month expenses"
            ) {
                onViewMore(TransactionSelector.TransactionSelectorEnum.LAST_MONTH)
            }
        }

        VerticalSpacer(height = 16)

        AddCustomTransaction {
            viewModel.addCustomTransaction()
        }
    }
}