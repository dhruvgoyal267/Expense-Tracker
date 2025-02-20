package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.CustomPrimaryButton
import `in`.expenses.expensetracker.utils.HorizontalSpacer
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun AddCustomTransaction(
    modifier: Modifier = Modifier,
    btnAction: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                colorResource(id = R.color.card_bg)
            )
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Add transaction",
                fontSize = 16.sp,
                color = colorResource(id = R.color.title),
                fontWeight = FontWeight.W500
            )
            VerticalSpacer(height = 4)
            Text(
                text = "Add your custom transaction\nwhich we missed to record",
                fontSize = 12.sp,
                color = colorResource(id = R.color.sub_title),
                lineHeight = 16.sp
            )
        }

        HorizontalSpacer(modifier = Modifier.weight(1f))

        CustomPrimaryButton(text = "Add", action = btnAction)
    }
}