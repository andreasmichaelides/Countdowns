package com.bitatron.countdowns.features.globalcountdowns.presentation

import com.bitatron.countdowns.features.globalcountdowns.domain.GetCategoriesUseCase
import com.bitatron.countdowns.features.globalcountdowns.domain.GetGlobalCountdownsUseCase
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCategory
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiSubCategory
import com.bitatron.snazzyrecycling.ClickedRecyclerItem
import com.bitatron.statestream.logger.Logger
import com.bitatron.statestream.presentation.StateViewModel
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class GlobalCountdownsViewModel(
    logger: Logger,
    schedulersProvider: SchedulersProvider,
    getCategoriesUseCase: GetCategoriesUseCase,
    itemClicked: PublishSubject<ClickedRecyclerItem>,
    getGlobalCountdownsUseCase: GetGlobalCountdownsUseCase
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
                }.subscribe { input().onNext(LoadCategoriesSuccessInput(it)) },

            viewModelAction().filter { it == LoadCountdownsViewModelAction }
                .flatMapSingle {
                    getGlobalCountdownsUseCase.execute()
                        .doOnError { logger.e(this, it) }
                        .doOnError { input().onNext(LoadCountdownsFailedInput(it)) }
                        .onErrorResumeNext(Single.never())
                }.subscribe { input().onNext(LoadCountdownsSuccessInput(it)) },

            itemClicked.filter { it.clickAction is CategoryClickAction }
                .map { it.recyclerItem as UiCategory to (it.clickAction as CategoryClickAction).isChecked}
                .doOnError { logger.e(this, it) }
                .onErrorResumeNext (Observable.never())
                .subscribe { input().onNext(SelectCategoryInput(it.first, it.second)) },

            itemClicked.filter { it.clickAction is SubCategoryClickAction }
                .map { it.recyclerItem as UiSubCategory to (it.clickAction as SubCategoryClickAction).isChecked}
                .doOnError { logger.e(this, it) }
                .onErrorResumeNext (Observable.never())
                .subscribe { input().onNext(SelectSubCategoryInput(it.first, it.second)) }
        )
    }

    fun loadCategories() {
        input().onNext(LoadCategoriesInput)
    }
}