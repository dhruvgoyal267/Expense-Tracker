package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerUI(onDateRangeSelected: (startDate: Long, endDate: Long) -> Unit) {
    val dateTime = LocalDateTime.now()
    val colors = DatePickerDefaults.colors(
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
    val dateRangePickerState = remember {
        DateRangePickerState(
            initialSelectedStartDateMillis = dateTime.minusDays(30).toMillis(),
            initialDisplayedMonthMillis = dateTime.toMillis(),
            initialSelectedEndDateMillis = dateTime.toMillis(),
            initialDisplayMode = DisplayMode.Picker,
            yearRange = 1970..dateTime.year,
            locale = Locale.ENGLISH,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= dateTime.toMillis()
                }
            }
        )
    }

    DatePickerDialog(
        colors = colors,
        onDismissRequest = {
            onDateRangeSelected(
                dateRangePickerState.selectedStartDateMillis ?: 0,
                dateRangePickerState.selectedEndDateMillis ?: 0
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onDateRangeSelected(
                    dateRangePickerState.selectedStartDateMillis ?: 0,
                    dateRangePickerState.selectedEndDateMillis ?: 0
                )
            }) {
                Text("OK", color = colorResource(id = R.color.tint))
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {},
            headline = {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Select date range",
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

fun LocalDateTime.toMillis() = atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()