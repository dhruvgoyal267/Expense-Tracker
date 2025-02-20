package `in`.expenses.expensetracker.usecases

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

interface ExtractAmountFromSmsUseCase {
    operator fun invoke(smsBody: String): String?
}

class ExtractAmountFromSmsUseCaseImpl @Inject constructor(): ExtractAmountFromSmsUseCase {
    override fun invoke(smsBody: String): String? {
        val regex = "(?:₹|Rs\\.?|INR)\\s?([\\d,]+(?:\\.\\d{1,2})?)"

        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(smsBody)

        return if (matcher.find()) {
            val amount = matcher.group(1)
            amount?.replace(",", "")
        } else {
            null
        }
    }
}