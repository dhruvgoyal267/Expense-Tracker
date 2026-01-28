package `in`.expenses.expensetracker.usecases

import javax.inject.Inject

interface ExtractAmountFromSmsUseCase {
    operator fun invoke(smsBody: String): String?
}

class ExtractAmountFromSmsUseCaseImpl @Inject constructor() : ExtractAmountFromSmsUseCase {
    override fun invoke(smsBody: String): String? {
        val parts: List<String> = smsBody.split("[\\s.]")
        for (part in parts) {
            if (part.matches("\\d+".toRegex())) {
                return part
            }
        }
        return null
    }
}