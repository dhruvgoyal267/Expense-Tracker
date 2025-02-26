package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.HorizontalSpacer
import `in`.expenses.expensetracker.utils.VerticalSpacer
import `in`.expenses.expensetracker.utils.formatAmount

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionUi(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    viewModel: MainViewModel
) {

    val deleteUpdateBottomSheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    if (showBottomSheet) {
        DeleteUpdateBottomSheetUI(
            transaction = transaction,
            sheetState = deleteUpdateBottomSheetState,
            dismiss = {
                showBottomSheet = false
            },
            onDelete = {
                showBottomSheet = false
                viewModel.deleteTransaction(transaction)
            },
            onUpdate = { newTransaction ->
                showBottomSheet = false
                viewModel.updateTransaction(transaction, newTransaction)
            }
        )
    }

    LaunchedEffect(key1 = showBottomSheet) {
        if (showBottomSheet) {
            deleteUpdateBottomSheetState.show()
        } else {
            deleteUpdateBottomSheetState.hide()
        }
    }


    Row(
        modifier = modifier
            .clickable {
                showBottomSheet = true
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = transaction.amount.toDoubleOrNull()?.formatAmount().orEmpty(),
                fontSize = 16.sp,
                color = colorResource(id = R.color.title)
            )
            VerticalSpacer(height = 2)
            Text(
                text = transaction.spendOn,
                fontSize = 12.sp,
                color = colorResource(id = R.color.sub_title)
            )
        }

        HorizontalSpacer(modifier = Modifier.weight(1f))

        Text(
            text = transaction.date,
            fontSize = 12.sp,
            color = colorResource(id = R.color.sub_title)
        )

    }
}