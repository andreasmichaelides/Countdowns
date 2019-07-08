package com.bitatron.countdowns.features.globalcountdowns.presentation

import com.bitatron.countdowns.features.globalcountdowns.domain.CategoryData
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCategory
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiSubCategory
import com.bitatron.statestream.presentation.*
import java.util.*

data class GlobalCountdownsUiModel(
    val isLoading: Boolean = false,
    val categories: List<UiCategory> = emptyList(),
    val subCategories: List<UiSubCategory> = emptyList()
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

