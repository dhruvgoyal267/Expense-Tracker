package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.HorizontalSpacer

@Composable
fun TitleUi(
    modifier: Modifier = Modifier,
    isBackEnabled: Boolean = false,
    onBackClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isBackEnabled) {
            Icon(
                modifier = Modifier.clickable {
                    onBackClicked()
                },
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back button"
            )
            HorizontalSpacer()
        }

        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.icon_app_logo),
            contentDescription = "App logo"
        )
        HorizontalSpacer()
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.app_name).lowercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = colorResource(id = R.color.title)
        )
    }
}