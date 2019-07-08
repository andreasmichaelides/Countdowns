package com.bitatron.countdowns.core.presentation.navigation

import com.bitatron.countdowns.R

class NavigationActionToActionIdMapper {

    fun map(navigationAction: NavigationAction) =
            when (navigationAction) {
//                HomeToSeriesList -> R.id.action_appBarHome_to_seriesListFragment
                else -> 0
            }

}