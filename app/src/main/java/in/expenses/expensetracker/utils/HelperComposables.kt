package `in`.expenses.expensetracker.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun CustomPrimaryButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, action: () -> Unit) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = action, colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.tint),
            disabledContainerColor = colorResource(id = R.color.card_bg_disabled),
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = text, color = colorResource(id = R.color.white), fontSize = 12.sp)
    }
}

@Composable
fun CustomSecondaryButton(modifier: Modifier = Modifier, text: String, action: () -> Unit) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.tint)),
        onClick = action,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(id = R.color.card_bg)
        ),
    ) {
        Text(text = text, color = colorResource(id = R.color.tint), fontSize = 12.sp)
    }
}