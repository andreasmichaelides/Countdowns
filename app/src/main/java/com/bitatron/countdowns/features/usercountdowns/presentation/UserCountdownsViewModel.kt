package com.bitatron.countdowns.features.usercountdowns.presentation

import com.bitatron.countdowns.core.presentation.CountdownNotificationClickAction
import com.bitatron.countdowns.core.presentation.model.UiCountdown
import com.bitatron.countdowns.features.usercountdowns.domain.GetUserCountdownsUseCase
import com.bitatron.snazzyrecycling.ClickedRecyclerItem
import com.bitatron.statestream.logger.Logger
import com.bitatron.statestream.presentation.StateViewModel
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class UserCountdownsViewModel(
    logger: Logger,
    schedulersProvider: SchedulersProvider,
    getUserCountdownsUseCase: GetUserCountdownsUseCase,
    itemClicked: PublishSubject<ClickedRecyclerItem>
) : StateViewModel<UserCountdownsUiModel>(
    UserCountdownsUiModel(),
    logger,
    schedulersProvider
) {

    init {
        subscriptions.addAll(
            viewModelAction().filter { it == LoadUserCountdownsViewModelAction }
                .flatMapSingle {
                    getUserCountdownsUseCase.execute()
                        .doOnError { logger.e(this, it) }
                        .doOnError { input().onNext(LoadCountdownsFailedInput(it)) }
                        .onErrorResumeNext(Single.never())
                }.subscribe { input().onNext(LoadUserCountdownsSuccessInput(it)) },

            itemClicked.filter { it.clickAction is CountdownNotificationClickAction }
                .map { it.recyclerItem as UiCountdown }
                .doOnError { logger.e(this, it) }
                .onErrorResumeNext(Observable.never())
                .subscribe { input().onNext(UserCountdownNotificationClickInput(it)) }
        )
    }

    fun loadData() {
        input().onNext(LoadUserCountdownsInput)
    }
}