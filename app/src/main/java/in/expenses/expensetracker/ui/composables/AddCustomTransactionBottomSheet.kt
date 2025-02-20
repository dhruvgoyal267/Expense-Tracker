package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.CustomPrimaryButton
import `in`.expenses.expensetracker.utils.VerticalSpacer

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

            Text(
                text = "Add transaction",
                fontSize = 16.sp,
                color = colorResource(id = R.color.title),
                fontWeight = FontWeight.W500
            )
            VerticalSpacer(height = 4)

            Text(
                text = "Add your custom transaction which we missed to record",
                fontSize = 12.sp,
                color = colorResource(id = R.color.sub_title),
                lineHeight = 16.sp
            )

            VerticalSpacer(height = 24)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount,
                onValueChange = {
                    amount = it
                    enableAddBtn = checkForEnableBtn(amount, spendOn)
                },
                textStyle = TextStyle.Default.copy(
                    color = colorResource(id = R.color.title),
                    fontSize = 12.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.tint),
                    focusedLabelColor = colorResource(id = R.color.tint)
                ),
                label = {
                    Text(text = "Amount", fontSize = 12.sp)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            VerticalSpacer()

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = spendOn,
                onValueChange = {
                    spendOn = it
                    enableAddBtn = checkForEnableBtn(amount, spendOn)
                },
                textStyle = TextStyle.Default.copy(
                    color = colorResource(id = R.color.title),
                    fontSize = 12.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.tint),
                    focusedLabelColor = colorResource(id = R.color.tint)
                ),
                label = {
                    Text(text = "Spend on", fontSize = 12.sp)
                })

            VerticalSpacer(height = 24)

            CustomPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Add transaction",
                enabled = enableAddBtn
            ) {
                viewModel.addTransaction(amount, spendOn)
            }
        }
    }
}

private fun checkForEnableBtn(amount: String, spendOn: String): Boolean {
    return amount.isNotBlank() && spendOn.isNotBlank()
}