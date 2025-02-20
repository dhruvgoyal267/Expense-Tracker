package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.CustomPrimaryButton
import `in`.expenses.expensetracker.utils.CustomSecondaryButton
import `in`.expenses.expensetracker.utils.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SMSPermissionBottomSheetUI(viewModel: MainViewModel, sheetState: SheetState) {
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.denySMSPermission()
        },
        sheetState = sheetState,
        containerColor = colorResource(id = R.color.card_bg)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Allow us to take the SMS permission",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.title)
            )

            VerticalSpacer()

            Text(
                text = "We will not use your sms anywhere, it will be just to detect a transaction, so that it can be logged automatically",
                fontSize = 12.sp,
                color = colorResource(id = R.color.sub_title)
            )

            VerticalSpacer(height = 24)

            CustomSecondaryButton(Modifier.fillMaxWidth(), text = "Deny Permission"){
                viewModel.denySMSPermission()
            }

            CustomPrimaryButton(modifier = Modifier.fillMaxWidth(), text = "Allow permission"){
                viewModel.askForSmsPermission()
            }
        }
    }
}