package `in`.expenses.expensetracker.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.AppState
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun HomeScreenUi(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.app_bg))
            .padding(horizontal = 16.dp),
    ) {
        TitleUi()

        VerticalSpacer(height = 16)

        AnimatedContent(targetState = viewModel.getCurrentState(), label = "") { state ->
            when (state) {
                AppState.NO_TRANSACTION_FOUND -> NoTransactionUI(
                    description = viewModel.getNoTransactionFoundDescription(),
                    btnAddAction = {
                        viewModel.addCustomTransaction()
                    }) {
                    viewModel.allowSMSPermission()
                }

                AppState.TRANSACTION_FOUND -> TransactionLayoutUi(viewModel = viewModel)
            }
        }
    }
}