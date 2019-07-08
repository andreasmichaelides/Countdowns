package com.bitatron.countdowns.core.presentation.navigation

import android.app.Activity
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bitatron.statestream.logger.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Wrapped all the Navigation Architecture Component calls here, to separate it from the rest of the app, in case we need to switch from it
 * if it stops being update, incommpatibilities, etc
 */
class ArchitectureComponentsNavigation(private val navigationActionToActionIdMapper: NavigationActionToActionIdMapper,
                                       private val logger: Logger
) : Navigation {

    override fun setupWithBottomNavigationView(activity: Activity,
                                               bottomNavigationView: BottomNavigationView,
                                               hostViewId: Int) {
        bottomNavigationView.setupWithNavController(findNavController(activity, hostViewId))
    }

    override fun onMenuItemSelected(activity: Activity, menuItem: MenuItem, hostViewId: Int): Boolean =
            NavigationUI.onNavDestinationSelected(menuItem, findNavController(activity, hostViewId))

    override fun navigate(view: View, navigationAction: NavigationAction) {
        try {
            view.findNavController().navigate(navigationActionToActionIdMapper.map(navigationAction))
        } catch (throwable: Throwable) {
            logger.e(this, throwable)
        }
    }

    override fun navigate(view: View, navigationDirections: NavigationDirections) {
        try {
            when (navigationDirections) {
                is ArchComponentsNavigationDirections -> view.findNavController().navigate(navigationDirections.navDirections)
                else -> logger.e(this, "Invalid instance of NavigationDirections $navigationDirections")
            }
        } catch (throwable: Throwable) {
            logger.e(this, throwable)
        }
    }
}

data class ArchComponentsNavigationDirections(val navDirections: NavDirections) : NavigationDirections()