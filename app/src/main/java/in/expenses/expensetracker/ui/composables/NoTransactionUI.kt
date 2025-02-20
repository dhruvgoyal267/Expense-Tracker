package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.CustomPrimaryButton
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun NoTransactionUI(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_transaction_found),
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            color = colorResource(
                id = R.color.title
            )
        )

        VerticalSpacer()

        Text(
            text = "No transaction found, while we are keeping an eye on your transactions you can add it on your own using button below",
            fontSize = 12.sp,
            color = colorResource(
                id = R.color.sub_title
            ),
            textAlign = TextAlign.Center
        )

        VerticalSpacer()

        CustomPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.add_transaction)
        ) {
            viewModel.addCustomTransaction()
        }
    }
}