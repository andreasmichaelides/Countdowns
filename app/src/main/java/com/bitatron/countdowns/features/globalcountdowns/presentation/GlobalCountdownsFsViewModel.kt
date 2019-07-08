package com.bitatron.countdowns.features.globalcountdowns.presentation

import com.bitatron.countdowns.features.globalcountdowns.domain.GetCategoriesUseCase
import com.bitatron.statestream.logger.Logger
import com.bitatron.statestream.presentation.StateViewModel
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Single

class GlobalCountdownsViewModel(
    logger: Logger,
    schedulersProvider: SchedulersProvider,
    getCategoriesUseCase: GetCategoriesUseCase
) : StateViewModel<GlobalCountdownsUiModel>(
    GlobalCountdownsUiModel(),
    logger,
    schedulersProvider
) {

    init {
        subscriptions.addAll(
            viewModelAction().filter { it == LoadCategoriesViewModelAction }
                .flatMapSingle {
                    getCategoriesUseCase.execute()
                        .doOnError { logger.e(this, it) }
                        .doOnError { input().onNext(LoadCategoriesFailedInput(it)) }
                        .onErrorResumeNext(Single.never())
                }.subscribe { input().onNext(LoadCategoriesSuccessInput(it)) }
        )
    }

    fun loadCategories() {
        input().onNext(LoadCategoriesInput)
    }
}