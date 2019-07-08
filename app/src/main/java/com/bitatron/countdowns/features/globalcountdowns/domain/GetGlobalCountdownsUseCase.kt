package com.bitatron.countdowns.features.globalcountdowns.domain

import com.bitatron.countdowns.core.domain.countdowns.Countdown
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Single
import org.joda.time.DateTime

class GetGlobalCountdownsUseCase(private val schedulersProvider: SchedulersProvider) {

    fun execute(): Single<List<Countdown>> {
        return Single.just(
            listOf(
                Countdown(
                    "1",
                    "Ryzen 3000 release",
                    DateTime.parse("21/7/2019"),
                    false,
                    false,
                    listOf("1"),
                    listOf("1-1")
                )
            )
        ).subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
    }

}