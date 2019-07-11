package com.bitatron.countdowns.features.globalcountdowns

import android.content.res.Resources
import com.bitatron.countdowns.R
import com.bitatron.countdowns.features.globalcountdowns.domain.GetCategoriesUseCase
import com.bitatron.countdowns.features.globalcountdowns.domain.GetGlobalCountdownsUseCase
import com.bitatron.countdowns.features.globalcountdowns.presentation.GlobalCountdownsViewModel
import com.bitatron.countdowns.features.globalcountdowns.presentation.addViewHolders
import com.bitatron.snazzyrecycling.PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val globalCountdownsModule = module {
    factory { GetGlobalCountdownsUseCase(get(), get<Resources>().getString(R.string.single_countdown_format)) }
    factory { GetCategoriesUseCase(get()) }

    viewModel { GlobalCountdownsViewModel(get(), get(), get(), get(named(PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED)), get()) }

    addViewHolders()
}