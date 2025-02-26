package `in`.expenses.expensetracker.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.DateTimeSelectorEnum
import `in`.expenses.expensetracker.utils.VerticalSpacer
import `in`.expenses.expensetracker.utils.formatDate
import java.util.Calendar

@Composable
fun AmountInputForm(
    amount: String,
    spendOn: String,
    date: String,
    onAmountChanged: (amount: String) -> Unit,
    onSpendOnChanged: (spendOn: String) -> Unit,
    onDateTimeChanged: (time: String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = amount,
        onValueChange = onAmountChanged,
        textStyle = TextStyle.Default.copy(
            color = colorResource(id = R.color.title),
            fontSize = 12.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.tint),
            focusedLabelColor = colorResource(id = R.color.tint)
        ),
        label = {
            Text(text = stringResource(id = R.string.amount), fontSize = 12.sp)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

    VerticalSpacer()

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = spendOn,
        onValueChange = onSpendOnChanged,
        textStyle = TextStyle.Default.copy(
            color = colorResource(id = R.color.title),
            fontSize = 12.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.tint),
            focusedLabelColor = colorResource(id = R.color.tint)
        ),
        label = {
            Text(text = stringResource(R.string.spend_on), fontSize = 12.sp)
        })

    VerticalSpacer()

    var dateTimeType by remember {
        mutableStateOf(DateTimeSelectorEnum.NONE)
    }

    val dateTime = Calendar.getInstance()
    var timeInMillis by remember {
        mutableStateOf(dateTime.timeInMillis)
    }

    AnimatedContent(targetState = dateTimeType, label = "dateTimeSelector") { selectorType ->
        when (selectorType) {
            DateTimeSelectorEnum.NONE -> Unit
            DateTimeSelectorEnum.DATE -> {
                DatePickerUI(onDateSelected = {
                    timeInMillis = it
                    dateTimeType = DateTimeSelectorEnum.TIME
                })
            }

            DateTimeSelectorEnum.TIME -> {
                TimePickerUI(onTimeSelected = { hour, minute ->
                    dateTimeType = DateTimeSelectorEnum.NONE
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = timeInMillis
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    onDateTimeChanged(calendar.timeInMillis.formatDate())
                })
            }
        }
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = date,
        readOnly = true,
        onValueChange = {},
        textStyle = TextStyle.Default.copy(
            color = colorResource(id = R.color.title),
            fontSize = 12.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.tint),
            focusedLabelColor = colorResource(id = R.color.tint)
        ),
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    dateTimeType = DateTimeSelectorEnum.DATE
                },
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Picker"
            )
        },
        label = {
            Text(text = stringResource(R.string.date), fontSize = 12.sp)
        })
}