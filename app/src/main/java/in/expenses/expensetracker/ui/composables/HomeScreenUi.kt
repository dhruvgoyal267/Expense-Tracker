package `in`.expenses.expensetracker.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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

        val appState by viewModel.appState.observeAsState(AppState.LOADING)

        LaunchedEffect(key1 = null) {
            viewModel.getCurrentState()
        }

        AnimatedContent(targetState = appState, label = "") { state ->
            when (state) {
                AppState.LOADING -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                AppState.NO_TRANSACTION_FOUND -> NoTransactionUI(viewModel = viewModel)

                AppState.TRANSACTION_FOUND -> TransactionLayoutUi(viewModel = viewModel)

                AppState.VIEW_ALL_TRANSACTION -> ViewAllTransactionsUI(viewModel = viewModel)
            }
        }
    }
}