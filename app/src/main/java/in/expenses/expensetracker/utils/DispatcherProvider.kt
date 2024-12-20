package `in`.expenses.expensetracker.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

class DispatcherProviderImpl @Inject constructor(): DispatcherProvider{
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}