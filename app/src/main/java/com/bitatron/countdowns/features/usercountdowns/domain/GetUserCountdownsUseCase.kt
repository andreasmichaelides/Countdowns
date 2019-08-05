package com.bitatron.countdowns.features.usercountdowns.domain

import com.bitatron.countdowns.core.domain.countdowns.Countdown
import com.bitatron.countdowns.core.domain.mapper.CountdownToUiCountDownMapper
import com.bitatron.countdowns.core.presentation.model.UiCountdown
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class GetUserCountdownsUseCase(private val schedulersProvider: SchedulersProvider,
                               private val countdownToUiCountDownMapper: CountdownToUiCountDownMapper) {

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
                    listOf("3-1"),
                    "Berlin",
                    0.0,
                    0.0,
                    true
                )
            )
        ).map { countdowns -> countdowns.map { countdownToUiCountDownMapper.map(it, dateTimeNow) } }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
    }

}