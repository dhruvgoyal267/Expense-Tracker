package `in`.expenses.expensetracker.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainScope