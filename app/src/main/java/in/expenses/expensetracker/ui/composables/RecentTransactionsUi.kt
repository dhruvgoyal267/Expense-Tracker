package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.MainViewModel
import `in`.expenses.expensetracker.utils.HorizontalSpacer
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun RecentTransactionUi(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onViewMore: (transactionSelector: TransactionSelector.TransactionSelectorEnum) -> Unit
) {
    val transactions by viewModel.recentTransaction.observeAsState(emptyList())

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.card_bg))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Recent Transaction",
            fontSize = 18.sp,
            color = colorResource(id = R.color.title),
            fontWeight = FontWeight.Bold
        )
        VerticalSpacer()
        LazyColumn {
            items(transactions) { transaction ->
                Column {
                    TransactionUi(
                        modifier = Modifier.padding(vertical = 8.dp),
                        transaction = transaction
                    ){}
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.separator_bg))
                    )
                }
            }
        }
        VerticalSpacer(height = 12)

        Row(
            modifier = Modifier
                .clickable {
                    onViewMore(TransactionSelector.TransactionSelectorEnum.ALL_TRANSACTION)
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "ViEW MORE", fontSize = 12.sp, color = colorResource(id = R.color.tint))
            HorizontalSpacer(width = 2)
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "View More",
                tint = colorResource(id = R.color.tint)
            )
        }
    }
}