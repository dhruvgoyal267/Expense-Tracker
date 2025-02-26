package `in`.expenses.expensetracker.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getDatePickerColors(): DatePickerColors {
    return DatePickerDefaults.colors(
        containerColor = colorResource(id = R.color.app_bg),
        titleContentColor = colorResource(id = R.color.title),
        headlineContentColor = colorResource(id = R.color.title),
        weekdayContentColor = colorResource(id = R.color.title),
        subheadContentColor = colorResource(id = R.color.title),
        yearContentColor = colorResource(id = R.color.title),
        currentYearContentColor = colorResource(id = R.color.title),
        selectedYearContainerColor = colorResource(id = R.color.title),
        disabledDayContentColor = colorResource(id = R.color.disabled_text),
        todayDateBorderColor = colorResource(id = R.color.tint),
        dayInSelectionRangeContainerColor = colorResource(id = R.color.tint).copy(alpha = 0.2f),
        dayInSelectionRangeContentColor = colorResource(id = R.color.tint),
        selectedDayContainerColor = colorResource(id = R.color.tint)
    )
}