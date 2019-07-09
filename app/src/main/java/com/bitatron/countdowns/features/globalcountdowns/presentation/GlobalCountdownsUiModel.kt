package com.bitatron.countdowns.features.globalcountdowns.presentation

import com.bitatron.countdowns.features.globalcountdowns.domain.CategoryData
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCategory
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCountdown
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiSubCategory
import com.bitatron.statestream.presentation.*
import java.util.*

data class GlobalCountdownsUiModel(
    val isLoading: Boolean = false,
    val categories: List<UiCategory> = emptyList(),
    val subCategories: List<UiSubCategory> = emptyList(),
    val subCategoriesToDisplay: List<UiSubCategory> = emptyList(),
    val countDowns: List<UiCountdown> = emptyList(),
    val countDownsToDisplay: List<UiCountdown> = emptyList()
) : UiModel(Stack(), Stack(), Stack())

object LoadCategoriesViewModelAction : ViewModelAction
object LoadCountdownsViewModelAction : ViewModelAction

object ShowLoadCategoriesErrorActivityAction : ActivityAction
object ShowLoadCountdownsErrorActivityAction : ActivityAction

object LoadCategoriesInput : Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel =
        uiModel.copy(isLoading = true)
            .push(LoadCountdownsViewModelAction)
            .push(LoadCategoriesViewModelAction)
}

data class LoadCategoriesSuccessInput(private val categoryData: CategoryData) : Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel =
        uiModel.copy(
            isLoading = false,
            categories = categoryData.categories,
            subCategories = categoryData.subCategories
        )
}

data class LoadCategoriesFailedInput(private val throwable: Throwable) : Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel =
        uiModel.copy(isLoading = false)
            .push(ShowLoadCategoriesErrorActivityAction)
}

data class LoadCountdownsSuccessInput(private val countdowns: List<UiCountdown>) : Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel =
        uiModel.copy(
            isLoading = false,
            countDowns = countdowns,
            countDownsToDisplay = countdowns
        )
}

data class LoadCountdownsFailedInput(private val throwable: Throwable) : Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel =
        uiModel.copy(isLoading = false)
            .push(ShowLoadCountdownsErrorActivityAction)
}

data class SelectCategoryInput(private val category: UiCategory, private val isChecked: Boolean) :Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel {
        val updatedCategory = category.copy(isSelected = isChecked)
        val categories = uiModel.categories.map { if (it.id == updatedCategory.id) updatedCategory else it.copy(isSelected = false) }

        var subCategoriesToDisplay =
            when (updatedCategory.isSelected) {
                true -> uiModel.subCategories.filter { it.categoryId == category.id }
                else -> emptyList()
            }
        subCategoriesToDisplay = if (subCategoriesToDisplay.size == 1) {
            emptyList()
        } else {
            subCategoriesToDisplay
        }

        val countdownsToDisplay = getCountdownsToDisplay(updatedCategory, uiModel)

        return uiModel.copy(categories = categories,
            subCategoriesToDisplay = subCategoriesToDisplay,
            countDownsToDisplay = countdownsToDisplay)
    }
}

data class SelectSubCategoryInput(private val subCategory: UiSubCategory, private val isChecked: Boolean) :Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel {
        val updatedSubCategory = subCategory.copy(isSelected = isChecked)
        val subCategoriesToDisplay = uiModel.subCategoriesToDisplay.map { if (it.id == updatedSubCategory.id) updatedSubCategory else it }

        val categoryCountDowns = getCountdownsToDisplay(uiModel.categories.first { it.isSelected }, uiModel)
        val selectedSubCategories = subCategoriesToDisplay.filter { it.isSelected }
        val countdownsToDisplay = when (selectedSubCategories.isNotEmpty()) {
            true -> categoryCountDowns.filter { uiCountdown -> uiCountdown.subCategories.any { subCategoryId -> selectedSubCategories.any { subCategoryId == it.id } } }
            else -> categoryCountDowns
        }

        return uiModel.copy(subCategoriesToDisplay = subCategoriesToDisplay, countDownsToDisplay = countdownsToDisplay)
    }
}

data class CountdownNotificationClickInput(private val uiCountdown: UiCountdown) :Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel {
        val updatedCountdown = uiCountdown.copy(isSetToNotify = uiCountdown.isSetToNotify.not())
        val updatedCountdowns = uiModel.countDowns.map { if (it.id == uiCountdown.id) updatedCountdown else it }
        val updatedCountdownsToDisplay = uiModel.countDownsToDisplay.map { if (it.id == uiCountdown.id) updatedCountdown else it }

        return uiModel.copy(countDowns = updatedCountdowns, countDownsToDisplay = updatedCountdownsToDisplay)
    }
}

data class CountdownBookmarkClickInput(private val uiCountdown: UiCountdown) :Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel {
        val updatedCountdown = uiCountdown.copy(isBookmarked = uiCountdown.isBookmarked.not())
        val updatedCountdowns = uiModel.countDowns.map { if (it.id == uiCountdown.id) updatedCountdown else it }
        val updatedCountdownsToDisplay = uiModel.countDownsToDisplay.map { if (it.id == uiCountdown.id) updatedCountdown else it }

        return uiModel.copy(countDowns = updatedCountdowns, countDownsToDisplay = updatedCountdownsToDisplay)
    }
}

private fun getCountdownsToDisplay(updatedCategory: UiCategory, uiModel: GlobalCountdownsUiModel): List<UiCountdown> {
    return when (updatedCategory.isSelected) {
        true -> uiModel.countDowns.filter { it.categories.contains(updatedCategory.id) }
        else -> uiModel.countDowns
    }
}