package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.VerticalSpacer
import `in`.expenses.expensetracker.utils.formatAmount

@Composable
fun ExpenseCardUI(
    modifier: Modifier = Modifier,
    amount: Double,
    description: String,
    cardAction: () -> Unit
) {
    val expense = if(amount == 0.0){
        "NA"
    } else {
        amount.formatAmount()
    }

    Column(
        modifier = modifier
            .clickable {
                cardAction()
            }
            .clip(RoundedCornerShape(8.dp))
            .background(
                colorResource(id = R.color.card_bg)
            )
            .padding(16.dp)
    ) {
        Text(
            text = expense,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.title)
        )

        VerticalSpacer(height = 2)

        Text(text = description, fontSize = 12.sp, color = colorResource(id = R.color.sub_title))
    }
}
