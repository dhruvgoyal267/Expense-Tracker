package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.CustomPrimaryButton
import `in`.expenses.expensetracker.utils.VerticalSpacer
import `in`.expenses.expensetracker.utils.checkForEnableBtn
import `in`.expenses.expensetracker.utils.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomTransactionBottomSheet(viewModel: MainViewModel, sheetState: SheetState) {
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.dismissTransactionBottomSheet()
        },
        containerColor = colorResource(id = R.color.card_bg),
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var enableAddBtn by remember {
                mutableStateOf(false)
            }

            var amount by remember {
                mutableStateOf(viewModel.preFetchedAmount)
            }

            var spendOn by remember {
                mutableStateOf("")
            }

            var dateTime by remember {
                mutableStateOf(System.currentTimeMillis().formatDate())
            }

            Text(
                text = stringResource(id = R.string.add_transaction),
                fontSize = 16.sp,
                color = colorResource(id = R.color.title),
                fontWeight = FontWeight.W500
            )
            VerticalSpacer(height = 4)

            Text(
                text = stringResource(id = R.string.add_your_custom_transaction_which_we_missed_to_record),
                fontSize = 12.sp,
                color = colorResource(id = R.color.sub_title),
                lineHeight = 16.sp
            )

            VerticalSpacer(height = 24)

            AmountInputForm(
                amount = amount,
                spendOn = spendOn,
                date = dateTime,
                onAmountChanged = {
                    amount = it
                    enableAddBtn = checkForEnableBtn(amount, spendOn)
                },
                onSpendOnChanged = {
                    spendOn = it
                    enableAddBtn = checkForEnableBtn(amount, spendOn)
                },
                onDateTimeChanged = {
                    dateTime = it
                }
            )

            VerticalSpacer(height = 24)

            CustomPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.add_transaction),
                enabled = enableAddBtn
            ) {
                viewModel.addTransaction(amount, spendOn, dateTime)
            }
        }
    }
}