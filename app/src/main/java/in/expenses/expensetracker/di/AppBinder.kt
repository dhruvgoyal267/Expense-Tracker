package `in`.expenses.expensetracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.expenses.expensetracker.datastore.AppDataStore
import `in`.expenses.expensetracker.datastore.AppDataStoreImpl
import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.repo.TransactionRepoImpl
import `in`.expenses.expensetracker.usecases.AddTransactionUseCase
import `in`.expenses.expensetracker.usecases.AddTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.CanAskPermissionUseCase
import `in`.expenses.expensetracker.usecases.CanAskPermissionUseCaseImpl
import `in`.expenses.expensetracker.usecases.CheckForTransactionSmsUseCase
import `in`.expenses.expensetracker.usecases.CheckForTransactionSmsUseCaseImpl
import `in`.expenses.expensetracker.usecases.DeleteTransactionUseCase
import `in`.expenses.expensetracker.usecases.DeleteTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.ExtractAmountFromSmsUseCase
import `in`.expenses.expensetracker.usecases.ExtractAmountFromSmsUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetAllTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetAllTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetCurrentMonthExpensesUseCase
import `in`.expenses.expensetracker.usecases.GetCurrentMonthExpensesUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetLastMonthExpensesUseCase
import `in`.expenses.expensetracker.usecases.GetLastMonthExpensesUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetNTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetNTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.OnPermissionAskedUseCase
import `in`.expenses.expensetracker.usecases.OnPermissionAskedUseCaseImpl
import `in`.expenses.expensetracker.usecases.UpdateTransactionUseCase
import `in`.expenses.expensetracker.usecases.UpdateTransactionUseCaseImpl
import `in`.expenses.expensetracker.utils.DispatcherProvider
import `in`.expenses.expensetracker.utils.DispatcherProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBinder {
    @Binds
    @Singleton
    abstract fun bindTransactionRepo(transactionRepoImpl: TransactionRepoImpl): TransactionRepo

    @Binds
    @Singleton
    abstract fun bindAppDataStore(appDataStoreImpl: AppDataStoreImpl): AppDataStore

    @Binds
    abstract fun bindDispatcherProvider(dispatcherProviderImpl: DispatcherProviderImpl): DispatcherProvider
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBinder {
    @Binds
    abstract fun bindAddTransactionUseCase(addTransactionUseCaseImpl: AddTransactionUseCaseImpl): AddTransactionUseCase

    @Binds
    abstract fun bindUpdateTransactionUseCase(updateTransactionUseCaseImpl: UpdateTransactionUseCaseImpl): UpdateTransactionUseCase

    @Binds
    abstract fun bindDeleteTransactionUseCase(deleteTransactionUseCaseImpl: DeleteTransactionUseCaseImpl): DeleteTransactionUseCase

    @Binds
    abstract fun bindGetAllTransactionUseCase(getAllTransactionUseCaseImpl: GetAllTransactionUseCaseImpl): GetAllTransactionUseCase

    @Binds
    abstract fun bindNTransactionUseCase(getNTransactionUseCaseImpl: GetNTransactionUseCaseImpl): GetNTransactionUseCase

    @Binds
    abstract fun bindGetCurrentMonthUseCase(getCurrentMonthExpensesUseCaseImpl: GetCurrentMonthExpensesUseCaseImpl): GetCurrentMonthExpensesUseCase

    @Binds
    abstract fun bindGetLastMonthUseCase(getLastMonthExpensesUseCaseImpl: GetLastMonthExpensesUseCaseImpl): GetLastMonthExpensesUseCase

    @Binds
    abstract fun bindCheckForTransactionSmsUseCase(checkForTransactionSmsUseCaseImpl: CheckForTransactionSmsUseCaseImpl): CheckForTransactionSmsUseCase

    @Binds
    abstract fun bindExtractAmountFromSmsUseCase(extractAmountFromSmsUseCaseImpl: ExtractAmountFromSmsUseCaseImpl): ExtractAmountFromSmsUseCase

    @Binds
    abstract fun bindPermissionAskedUseCase(onPermissionAskedUseCaseImpl: OnPermissionAskedUseCaseImpl): OnPermissionAskedUseCase

    @Binds
    abstract fun bindCanAskPermissionUseCase(canAskPermissionUseCaseImpl: CanAskPermissionUseCaseImpl): CanAskPermissionUseCase
}