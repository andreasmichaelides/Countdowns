package com.bitatron.countdowns.features.globalcountdowns.domain

import com.bitatron.countdowns.core.domain.countdowns.Countdown
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCountdown
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat

class GetGlobalCountdownsUseCase(private val schedulersProvider: SchedulersProvider) {

    fun execute(): Single<List<UiCountdown>> {

        val dateTimeNow = DateTime.now()
        val dateTimeFormatter = DateTimeFormat.forPattern("MMM d, yyyy 'at' hh:mm:ss a z")

        return Single.just(
            listOf(
                Countdown(
                    "1",
                    "Berlin flight",
                    DateTime.parse("July 16, 2019 at 06:30:00 AM GMT", dateTimeFormatter),
                    false,
                    false,
                    listOf("3"),
                    listOf("3-1")
                )
            )
        ).map { countdowns ->
            countdowns.map {
                UiCountdown(
                    it.id,
                    it.name,
                    it.endTime.toString(),
                    Days.daysBetween(dateTimeNow, it.endTime).days.toString(),
                    it.categories,
                    it.subCategories
                )
            }
        }.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
    }

}