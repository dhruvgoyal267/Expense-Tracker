package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.getDatePickerColors
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerUI(onTimeSelected: (hour: Int, minute: Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val colors = getDatePickerColors()
    val timePickerState = remember {
        TimePickerState(
            initialHour = calendar.get(Calendar.HOUR_OF_DAY),
            initialMinute = calendar.get(Calendar.MINUTE),
            is24Hour = false
        )
    }

    DatePickerDialog(
        colors = colors,
        onDismissRequest = {
            onTimeSelected(
                timePickerState.hour,
                timePickerState.minute
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(
                    timePickerState.hour,
                    timePickerState.minute
                )
            }) {
                Text("OK", color = colorResource(id = R.color.tint))
            }
        }
    ) {
        TimePicker(
            state = timePickerState,
            colors = TimePickerColors(
                clockDialColor = colorResource(id = R.color.card_bg),
                selectorColor = colorResource(id = R.color.tint),
                containerColor = colorResource(id = R.color.card_bg),
                periodSelectorBorderColor = colorResource(id = R.color.tint),
                clockDialSelectedContentColor = colorResource(id = R.color.white),
                clockDialUnselectedContentColor = colorResource(id = R.color.title),
                periodSelectorSelectedContainerColor = colorResource(id = R.color.tint),
                periodSelectorSelectedContentColor = colorResource(id = R.color.white),
                periodSelectorUnselectedContainerColor = colorResource(id = R.color.app_bg),
                periodSelectorUnselectedContentColor = colorResource(id = R.color.title),
                timeSelectorSelectedContainerColor = colorResource(id = R.color.tint),
                timeSelectorSelectedContentColor = colorResource(id = R.color.white),
                timeSelectorUnselectedContainerColor = colorResource(id = R.color.app_bg),
                timeSelectorUnselectedContentColor = colorResource(id = R.color.title)
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}