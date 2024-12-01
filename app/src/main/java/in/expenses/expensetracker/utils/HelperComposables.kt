package `in`.expenses.expensetracker.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R

@Composable
fun VerticalSpacer(modifier: Modifier = Modifier, height: Int = 8) {
    Spacer(
        modifier = modifier.height(height.dp)
    )
}

@Composable
fun HorizontalSpacer(modifier: Modifier = Modifier, width: Int = 8) {
    Spacer(
        modifier = modifier
            .width(width.dp)
    )
}

@Composable
fun CustomButton(modifier: Modifier = Modifier, text: String, action: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = action, colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.tint)
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = text)
    }
}