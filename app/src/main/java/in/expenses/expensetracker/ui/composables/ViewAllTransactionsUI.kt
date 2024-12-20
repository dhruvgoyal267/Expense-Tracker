package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.ui.MainViewModel

@Composable
fun ViewAllTransactionsUI(viewModel: MainViewModel) {
    val transactions by viewModel.allTransaction.observeAsState(emptyList())
    LazyColumn(
        modifier = Modifier
            .background(
                colorResource(id = R.color.card_bg),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        items(transactions) { transaction ->
            Column {
                TransactionUi(
                    modifier = Modifier.padding(vertical = 8.dp),
                    transaction = transaction
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.separator_bg))
                )
            }
        }
    }
}