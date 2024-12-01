package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun HomeScreenUi(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.app_bg)),
    ) {
        TitleUi()
        VerticalSpacer(height = 16)
        RecentTransactionUi(
            transactions = viewModel.getRecentTransaction(),
            onViewAllClick = {
                viewModel.viewMoreTransactionClicked()
            }
        )
    }
}