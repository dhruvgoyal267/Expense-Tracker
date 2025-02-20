package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.utils.CustomPrimaryButton

@Composable
fun CustomActionTileUI(
    modifier: Modifier = Modifier,
    title: String,
    desc: String,
    cta: String,
    btnAction: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                colorResource(id = R.color.card_bg)
            )
            .padding(16.dp)
    ) {
        val (tvTitle, tvDesc, tvCta) = createRefs()

        Text(
            modifier = Modifier.constrainAs(tvTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(tvCta.start, margin = 16.dp)
                bottom.linkTo(tvDesc.top)
                width = Dimension.fillToConstraints
            },
            text = title,
            fontSize = 16.sp,
            color = colorResource(id = R.color.title),
            fontWeight = FontWeight.W500
        )
        Text(
            modifier = Modifier.constrainAs(tvDesc) {
                start.linkTo(tvTitle.start)
                top.linkTo(tvTitle.bottom, margin = 4.dp)
                end.linkTo(tvCta.start)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
            text = desc,
            fontSize = 12.sp,
            color = colorResource(id = R.color.sub_title),
            lineHeight = 16.sp
        )
        CustomPrimaryButton(modifier = Modifier.constrainAs(tvCta) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            start.linkTo(tvDesc.end, margin = 16.dp)
        }, text = cta, action = btnAction)
    }
}