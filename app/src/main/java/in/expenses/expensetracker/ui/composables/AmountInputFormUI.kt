package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun AmountInputForm(
    amount: String,
    spendOn: String,
    onAmountChanged: (amount: String) -> Unit,
    onSpendOnChanged: (spendOn: String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = amount,
        onValueChange = onAmountChanged,
        textStyle = TextStyle.Default.copy(
            color = colorResource(id = R.color.title),
            fontSize = 12.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.tint),
            focusedLabelColor = colorResource(id = R.color.tint)
        ),
        label = {
            Text(text = stringResource(id = R.string.amount), fontSize = 12.sp)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

    VerticalSpacer()

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = spendOn,
        onValueChange = onSpendOnChanged,
        textStyle = TextStyle.Default.copy(
            color = colorResource(id = R.color.title),
            fontSize = 12.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.tint),
            focusedLabelColor = colorResource(id = R.color.tint)
        ),
        label = {
            Text(text = stringResource(R.string.spend_on), fontSize = 12.sp)
        })
}