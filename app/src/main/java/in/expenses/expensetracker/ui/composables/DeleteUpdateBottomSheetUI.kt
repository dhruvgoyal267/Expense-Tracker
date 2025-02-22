package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.Transaction
import `in`.expenses.expensetracker.utils.CustomPrimaryButton
import `in`.expenses.expensetracker.utils.CustomSecondaryButton
import `in`.expenses.expensetracker.utils.HorizontalSpacer
import `in`.expenses.expensetracker.utils.VerticalSpacer
import `in`.expenses.expensetracker.utils.checkForEnableBtn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteUpdateBottomSheetUI(
    transaction: Transaction,
    sheetState: SheetState,
    dismiss: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: (updatedTransaction: Transaction) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = dismiss,
        containerColor = colorResource(id = R.color.card_bg),
        sheetState = sheetState
    ) {
        var enableAddBtn by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            var amount: String by remember {
                mutableStateOf(transaction.amount.toString())
            }

            var spendOn: String by remember {
                mutableStateOf(transaction.spendOn)
            }

            AmountInputForm(
                amount = amount,
                spendOn = spendOn,
                onAmountChanged = {
                    amount = it
                    enableAddBtn = checkForEnableBtn(amount, spendOn)
                },
                onSpendOnChanged = {
                    spendOn = it
                    enableAddBtn = checkForEnableBtn(amount, spendOn)
                }
            )

            VerticalSpacer(height = 24)

            Row(modifier = Modifier.fillMaxWidth()) {
                CustomSecondaryButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.delete)
                ) {
                    onDelete()
                }

                HorizontalSpacer()

                CustomPrimaryButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.update),
                    enabled = enableAddBtn
                ) {
                    onUpdate(
                        transaction.copy(
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            spendOn = spendOn
                        )
                    )
                }
            }
        }
    }
}