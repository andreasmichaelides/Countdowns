package com.bitatron.countdowns.features.globalcountdowns

import com.bitatron.countdowns.features.globalcountdowns.domain.GetCategoriesUseCase
import com.bitatron.countdowns.features.globalcountdowns.domain.GetGlobalCountdownsUseCase
import com.bitatron.countdowns.features.globalcountdowns.presentation.GlobalCountdownsViewModel
import com.bitatron.countdowns.features.globalcountdowns.presentation.addViewHolders
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val globalCountdownsModule = module {
    factory { GetGlobalCountdownsUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }

    viewModel { GlobalCountdownsViewModel(get(), get(), get()) }

    addViewHolders()
}