package com.bitatron.countdowns.features.addcountdown.presentation

import com.bitatron.statestream.logger.Logger
import com.bitatron.statestream.presentation.StateViewModel
import com.bitatron.statestream.schedulers.SchedulersProvider

class AddCountdownViewModel(logger: Logger,
schedulersProvider: SchedulersProvider)
    : StateViewModel<AddCountdownUiModel>(AddCountdownUiModel(),
        logger,
        schedulersProvider) {

        init {
        subscriptions.addAll(

        )
        }

}