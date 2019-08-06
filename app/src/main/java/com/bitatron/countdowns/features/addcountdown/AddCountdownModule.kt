package com.bitatron.countdowns.features.addcountdown

import com.bitatron.countdowns.features.addcountdown.presentation.AddCountdownViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addCountdownModule = module {
    viewModel { AddCountdownViewModel(get(), get()) }
}