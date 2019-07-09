package com.bitatron.countdowns.features.globalcountdowns.presentation

import com.bitatron.countdowns.features.globalcountdowns.domain.CategoryData
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCategory
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiSubCategory
import com.bitatron.statestream.presentation.*
import java.util.*

data class GlobalCountdownsUiModel(
    val isLoading: Boolean = false,
    val categories: List<UiCategory> = emptyList(),
    val subCategories: List<UiSubCategory> = emptyList(),
    val subCategoriesToDisplay: List<UiSubCategory> = emptyList()
) : UiModel(Stack(), Stack(), Stack())

object LoadCategoriesViewModelAction : ViewModelAction

object ShowLoadCategoriesErrorActivityAction : ActivityAction

object LoadCategoriesInput : Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel =
        uiModel.copy(isLoading = true)
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


        return uiModel.copy(categories = categories, subCategoriesToDisplay = subCategoriesToDisplay)
    }
}

data class SelectSubCategoryInput(private val subCategory: UiSubCategory, private val isChecked: Boolean) :Input<GlobalCountdownsUiModel> {
    override fun transformState(uiModel: GlobalCountdownsUiModel): GlobalCountdownsUiModel {
        val updatedSubCategory = subCategory.copy(isSelected = isChecked)
        val subCategoriesToDisplay = uiModel.subCategoriesToDisplay.map { if (it.id == updatedSubCategory.id) updatedSubCategory else it }

        return uiModel.copy(subCategoriesToDisplay = subCategoriesToDisplay)
    }
}

