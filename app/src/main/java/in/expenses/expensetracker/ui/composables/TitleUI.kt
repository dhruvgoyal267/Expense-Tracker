package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.HorizontalSpacer

@Composable
fun TitleUi() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.icon_app_logo),
            contentDescription = "App logo"
        )
        HorizontalSpacer()
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.app_name).lowercase()
        )
    }
}