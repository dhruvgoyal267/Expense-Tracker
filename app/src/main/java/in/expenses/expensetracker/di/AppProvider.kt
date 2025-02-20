package `in`.expenses.expensetracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import `in`.expenses.expensetracker.db.AppDb
import `in`.expenses.expensetracker.db.TransactionDao
import `in`.expenses.expensetracker.utils.AppConstants
import `in`.expenses.expensetracker.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
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

    @Provides
    @IoScope
    fun provideIoCoroutineScope(dispatcherProvider: DispatcherProvider): CoroutineScope {
        return CoroutineScope(dispatcherProvider.io)
    }

    @Provides
    @DefaultScope
    fun provideDefaultCoroutineScope(dispatcherProvider: DispatcherProvider): CoroutineScope {
        return CoroutineScope(dispatcherProvider.default)
    }

    @Provides
    @MainScope
    fun provideMainCoroutineScope(dispatcherProvider: DispatcherProvider): CoroutineScope {
        return CoroutineScope(dispatcherProvider.main)
    }

    @Provides
    @Named(AppConstants.BANKS)
    fun provideBankList(): List<String> {
        return listOf(
            "SBI",      // State Bank of India
            "PNB",      // Punjab National Bank
            "BOB",      // Bank of Baroda
            "UBI",      // Union Bank of India
            "CBI",      // Central Bank of India
            "IOB",      // Indian Overseas Bank
            "UCO",      // UCO Bank
            "BOM",      // Bank of Maharashtra
            "HDFC",     // HDFC Bank
            "ICICI",    // ICICI Bank
            "AXIS",     // Axis Bank
            "KMB",      // Kotak Mahindra Bank
            "YES",      // Yes Bank
            "IDFC",     // IDFC First Bank
            "RBL",      // Ratnakar Bank Limited
            "SIB",      // South Indian Bank
            "FED",      // Federal Bank
            "CITI",     // Citi Bank
            "SCB",      // Standard Chartered Bank
            "HSBC",     // HSBC Bank
            "DBS",      // DBS Bank
            "BOA",      // Bank of America
            "AU SFB",   // AU Small Finance Bank
            "UJJIVAN",  // Ujjivan Small Finance Bank
            "EQUITAS",  // Equitas Small Finance Bank
            "FINCARE",  // Fincare Small Finance Bank
            "ESAF",     // ESAF Small Finance Bank
            "AIRPAY",   // Airtel Payments Bank
            "PAYTM",    // Paytm Payments Bank
            "IPPB",     // India Post Payments Bank
            "JIOPAY",   // Jio Payments Bank
            "FINO",     // Fino Payments Bank
            "APGVB",    // Andhra Pradesh Grameena Vikas Bank
            "KGB",      // Kerala Gramin Bank
            "VGB"       // Vidarbha Konkan Gramin Bank
        )
    }

    @Provides
    @Named(AppConstants.DEBIT_TRANSACTION_TYPE)
    fun provideDebitTransactionType(): List<String> {
        return listOf(
            "DR",        // Debit
            "WITHDRAWN", // Withdrawn
            "DEBITED",   // Debited,
            "SENT"      //Sent,
        )
    }
}