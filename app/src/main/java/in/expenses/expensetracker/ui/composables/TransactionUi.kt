package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.utils.HorizontalSpacer
import `in`.expenses.expensetracker.utils.VerticalSpacer
import `in`.expenses.expensetracker.utils.formatAmount

@Composable
fun TransactionUi(modifier: Modifier = Modifier, transaction: Transaction, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = formatAmount(transaction.amount),
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