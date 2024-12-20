package `in`.expenses.expensetracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.expenses.expensetracker.db.AppDb
import `in`.expenses.expensetracker.db.TransactionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvider {
    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext applicationContext: Context): AppDb {
        return Room.databaseBuilder(
            applicationContext,
            AppDb::class.java, AppDb.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(appDb: AppDb): TransactionDao{
        return appDb.transactionDao()
    }
}