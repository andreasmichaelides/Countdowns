package com.bitatron.countdowns.features.usercountdowns.presentation

import com.bitatron.countdowns.core.presentation.model.UiCountdown
import com.bitatron.statestream.presentation.*
import java.util.*

data class UserCountdownsUiModel(
    val isLoading: Boolean = false,
    val countDowns: List<UiCountdown> = emptyList()
) : UiModel(Stack(), Stack(), Stack())

object LoadUserCountdownsViewModelAction : ViewModelAction

object ShowLoadCountdownsErrorActivityAction : ActivityAction
object EditCountdownActivityAction : ActivityAction

object LoadUserCountdownsInput : Input<UserCountdownsUiModel> {
    override fun transformState(uiModel: UserCountdownsUiModel): UserCountdownsUiModel =
        uiModel.copy(isLoading = true)
            .push(LoadUserCountdownsViewModelAction)
}

data class LoadUserCountdownsSuccessInput(private val countdowns: List<UiCountdown>) : Input<UserCountdownsUiModel> {
    override fun transformState(uiModel: UserCountdownsUiModel): UserCountdownsUiModel =
        uiModel.copy(
            isLoading = false,
            countDowns = countdowns
        )
}

data class LoadCountdownsFailedInput(private val throwable: Throwable) : Input<UserCountdownsUiModel> {
    override fun transformState(uiModel: UserCountdownsUiModel): UserCountdownsUiModel =
        uiModel.copy(isLoading = false)
            .push(ShowLoadCountdownsErrorActivityAction)
}

data class UserCountdownNotificationClickInput(private val uiCountdown: UiCountdown) : Input<UserCountdownsUiModel> {
    override fun transformState(uiModel: UserCountdownsUiModel): UserCountdownsUiModel {
        val updatedCountdown = uiCountdown.copy(isSetToNotify = uiCountdown.isSetToNotify.not())
        val updatedCountdowns = uiModel.countDowns.map { if (it.id == uiCountdown.id) updatedCountdown else it }
        return uiModel.copy(countDowns = updatedCountdowns)
    }
}

data class EditUserCountdownClickInput(private val uiCountdown: UiCountdown) : Input<UserCountdownsUiModel> {
    override fun transformState(uiModel: UserCountdownsUiModel): UserCountdownsUiModel {
        return uiModel.push(EditCountdownActivityAction)
    }
}

