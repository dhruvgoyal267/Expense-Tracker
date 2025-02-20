package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import `in`.expenses.expensetracker.R
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
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        RecentTransactionUi(viewModel = viewModel, onViewMore = onViewMore)

        VerticalSpacer(height = 16)

        val currentMonthExpense by viewModel.currentMonthExpense.observeAsState(0.0)
        val lastMonthExpense by viewModel.lastMonthExpense.observeAsState(0.0)
        val shouldShowAskSmsPermissionNudge by viewModel.shouldShowAskSmsPermissionNudge.observeAsState(false)

        LaunchedEffect(key1 = viewModel) {
            viewModel.loadExpenses()
        }

        Row {
            ExpenseCardUI(
                modifier = Modifier
                    .weight(1f),
                amount = currentMonthExpense,
                description = stringResource(
                    R.string.current_month_expenses
                )
            ) {
                onViewMore(TransactionSelector.TransactionSelectorEnum.CURRENT_MONTH)
            }

            HorizontalSpacer()

            ExpenseCardUI(
                modifier = Modifier
                    .weight(1f),
                amount = lastMonthExpense,
                description = stringResource(
                    R.string.last_month_expenses
                )
            ) {
                onViewMore(TransactionSelector.TransactionSelectorEnum.LAST_MONTH)
            }
        }

        VerticalSpacer(height = 16)

        CustomActionTileUI(
            title = stringResource(id = R.string.add_transaction),
            desc = stringResource(id = R.string.add_your_custom_transaction_which_we_missed_to_record),
            cta = stringResource(id = R.string.add)
        ) {
            viewModel.addCustomTransaction()
        }

        if (shouldShowAskSmsPermissionNudge) {
            VerticalSpacer(height = 16)

            CustomActionTileUI(
                title = stringResource(R.string.allow_us_to_take_the_sms_permission),
                desc = stringResource(
                    id = R.string.sms_permission_desc
                ),
                cta = stringResource(id = R.string.allow)
            ) {
                viewModel.askForSmsPermission()
            }
        }

        VerticalSpacer(height = 16)
    }
}