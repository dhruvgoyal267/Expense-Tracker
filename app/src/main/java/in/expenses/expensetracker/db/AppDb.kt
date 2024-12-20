package `in`.expenses.expensetracker.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {

    companion object{
        const val DB_NAME = "app_database"
    }

    abstract fun transactionDao(): TransactionDao
}