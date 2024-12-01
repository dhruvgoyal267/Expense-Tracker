package `in`.expenses.expensetracker.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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