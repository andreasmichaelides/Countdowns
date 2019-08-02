package com.bitatron.countdowns.features.globalcountdowns.domain

import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCategory
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiSubCategory
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Single

class GetCategoriesUseCase(private val schedulersProvider: SchedulersProvider) {

    fun execute(): Single<CategoryData> {
        return Single.just(
            CategoryData(
                listOf(
                    UiCategory("1", "Entertainment", false),
                    UiCategory("2", "Computing", false),
                    UiCategory("3", "Travel", false)
                ),
                listOf(
                    UiSubCategory("1-1", "1", "Video Games", false),
                    UiSubCategory("1-2", "1", "TV Series", false),
                    UiSubCategory("1-3", "1", "Movies", false),
                    UiSubCategory("2-1", "2", "Hardware", false),
                    UiSubCategory("3-1", "3", "Holidays", false),
                    UiSubCategory("3-2", "3", "Business", false)
                )
            )
        ).subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
    }

}

data class CategoryData(
    val categories: List<UiCategory>,
    val subCategories: List<UiSubCategory>
)