package com.bitatron.countdowns.core

import com.bitatron.countdowns.core.presentation.addViewHolders
import com.bitatron.countdowns.core.presentation.navigation.ArchitectureComponentsNavigation
import com.bitatron.countdowns.core.presentation.navigation.Navigation
import com.bitatron.countdowns.core.presentation.navigation.NavigationActionToActionIdMapper
import org.koin.dsl.module

val coreModule = module {
    factory { NavigationActionToActionIdMapper() }
    factory<Navigation> { ArchitectureComponentsNavigation(get(), get()) }

    addViewHolders()
}