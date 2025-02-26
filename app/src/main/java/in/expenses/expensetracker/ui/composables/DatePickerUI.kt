package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.getDatePickerColors
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUI(onDateSelected: (date: Long) -> Unit) {
    val dateTime = Calendar.getInstance()
    val finalDateTime = dateTime.timeInMillis
    dateTime.add(Calendar.DATE, -30)
    val initialDateTime = dateTime.timeInMillis
    dateTime.add(Calendar.DATE, 30)
    val colors = getDatePickerColors()
    val datePickerState = remember {
        DatePickerState(
            initialSelectedDateMillis = initialDateTime,
            initialDisplayedMonthMillis = finalDateTime,
            initialDisplayMode = DisplayMode.Picker,
            yearRange = 1970..dateTime.get(Calendar.YEAR),
            locale = Locale.ENGLISH,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= dateTime.timeInMillis
                }
            }
        )
    }

    DatePickerDialog(
        colors = colors,
        onDismissRequest = {
            onDateSelected(datePickerState.selectedDateMillis?:0)
        },
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis?:0)
            }) {
                Text(text = stringResource(R.string.ok), color = colorResource(id = R.color.tint))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {},
            headline = {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(
                        R.string.select_date
                    ),
                    color = colorResource(id = R.color.title)
                )
            },
            showModeToggle = false,
            colors = colors,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}