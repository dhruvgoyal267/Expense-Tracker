package `in`.expenses.expensetracker.datastore

interface AppDataStore {
    suspend fun permissionAsked()
    suspend fun permissionAskedCount(): Int
    suspend fun isSmsProcessingDone(): Boolean
    suspend fun smsProcessingDone()
}