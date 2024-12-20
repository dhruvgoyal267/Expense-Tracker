package `in`.expenses.expensetracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import `in`.expenses.expensetracker.repo.TransactionRepo
import `in`.expenses.expensetracker.repo.TransactionRepoImpl
import `in`.expenses.expensetracker.usecases.AddTransactionUseCase
import `in`.expenses.expensetracker.usecases.AddTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.DeleteTransactionUseCase
import `in`.expenses.expensetracker.usecases.DeleteTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetAllTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetAllTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetCurrentMonthExpensesUseCase
import `in`.expenses.expensetracker.usecases.GetCurrentMonthExpensesUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetLastMonthExpensesUseCase
import `in`.expenses.expensetracker.usecases.GetLastMonthExpensesUseCaseImpl
import `in`.expenses.expensetracker.usecases.GetNTransactionUseCase
import `in`.expenses.expensetracker.usecases.GetNTransactionUseCaseImpl
import `in`.expenses.expensetracker.usecases.UpdateTransactionUseCase
import `in`.expenses.expensetracker.usecases.UpdateTransactionUseCaseImpl
import `in`.expenses.expensetracker.utils.DispatcherProvider
import `in`.expenses.expensetracker.utils.DispatcherProviderImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppBinder {
    @Binds
    abstract fun bindTransactionRepo(transactionRepoImpl: TransactionRepoImpl): TransactionRepo

    @Binds
    abstract fun bindDispatcherProvider(dispatcherProviderImpl: DispatcherProviderImpl): DispatcherProvider
}

@Module
@InstallIn(ViewModelComponent::class)
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
}