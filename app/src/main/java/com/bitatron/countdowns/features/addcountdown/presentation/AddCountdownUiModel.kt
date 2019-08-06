package com.bitatron.countdowns.features.addcountdown.presentation

import com.bitatron.statestream.presentation.Input
import com.bitatron.statestream.presentation.UiModel
import java.util.*

data class AddCountdownUiModel(
val isLoading: Boolean = false
) :UiModel(Stack(), Stack(), Stack())

object ExampleLoadDataInput : Input<AddCountdownUiModel> {
    override fun transformState(uiModel: AddCountdownUiModel): AddCountdownUiModel =
    AddCountdownUiModel(true)
}