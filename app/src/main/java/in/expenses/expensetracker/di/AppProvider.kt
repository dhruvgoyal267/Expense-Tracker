package `in`.expenses.expensetracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.expenses.expensetracker.db.AppDb
import `in`.expenses.expensetracker.db.TransactionDao

@Module
@InstallIn(ViewModelComponent::class)
object AppProvider {
    @Provides
    fun provideAppDb(@ApplicationContext applicationContext: Context): AppDb {
        return Room.databaseBuilder(
            applicationContext,
            AppDb::class.java, AppDb.DB_NAME
        ).build()
    }

    @Provides
    fun provideTransactionDao(appDb: AppDb): TransactionDao{
        return appDb.transactionDao()
    }
}