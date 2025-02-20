package `in`.expenses.expensetracker.datastore

interface AppDataStore {
    suspend fun permissionAsked()
    suspend fun permissionAskedCount(): Int
}