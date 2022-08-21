package com.tui.app.module

import com.tui.app.network.ApiService
import com.tui.app.ui.presentation.view.conversion.ConversionRepository
import com.tui.app.ui.presentation.view.currency_selection.CurrencySelectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = CurrencySelectionRepository(apiService)

    @Singleton
    @Provides
    fun providesChallengeRepository(apiService: ApiService) = ConversionRepository(apiService)
}