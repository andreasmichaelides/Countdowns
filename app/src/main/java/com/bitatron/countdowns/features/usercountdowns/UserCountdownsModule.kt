package com.bitatron.countdowns.features.usercountdowns

import com.bitatron.countdowns.features.usercountdowns.domain.GetUserCountdownsUseCase
import com.bitatron.countdowns.features.usercountdowns.presentation.UserCountdownsViewModel
import com.bitatron.snazzyrecycling.PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userCountdownsModule = module {

    factory { GetUserCountdownsUseCase(get(), get()) }
    viewModel { UserCountdownsViewModel(get(), get(), get(), get(named(PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED))) }
}