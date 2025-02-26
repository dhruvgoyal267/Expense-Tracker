package `in`.expenses.expensetracker.usecases

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.ProcessingState
import `in`.expenses.expensetracker.utils.VerticalSpacer

@Composable
fun ProcessingUI(
    processingState: ProcessingState,
    @StringRes titleSrc: Int,
    onProcessingDone: (isSuccess: Boolean) -> Unit
) {
    when (processingState) {
        ProcessingState.Default -> Unit
        is ProcessingState.Processed -> onProcessingDone(processingState.isError.not())
        is ProcessingState.Processing -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colorResource(id = R.color.card_bg),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(
                        id = titleSrc,
                        processingState.processed,
                        processingState.total
                    ),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.title),
                    fontWeight = FontWeight.W500
                )

                VerticalSpacer()

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = {
                        processingState.processed.toFloat() / processingState.total
                    },
                    color = colorResource(
                        id = R.color.tint,
                    ),
                    trackColor = colorResource(id = R.color.app_bg)
                )

                VerticalSpacer()

                Text(
                    text = stringResource(R.string.please_do_not_close_the_app_until_we_finish_processing_your_sms_messages),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.sub_title),
                    lineHeight = 16.sp
                )
            }

            VerticalSpacer(height = 16)
        }
    }
}