package com.bitatron.countdowns.features.globalcountdowns.domain

import com.bitatron.countdowns.core.domain.countdowns.Countdown
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.RemainingTime
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCountdown
import com.bitatron.statestream.schedulers.SchedulersProvider
import io.reactivex.Single
import org.joda.time.*
import org.joda.time.format.DateTimeFormat

class GetGlobalCountdownsUseCase(
    private val schedulersProvider: SchedulersProvider,
    private val countdownFormat: String
) {

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
                    0.0
                )
            )
        ).map { countdowns ->
            countdowns.map {
                UiCountdown(
                    it.id,
                    it.name,
                    it.endTime.toString(),
                    getRemainingTimeText(it.endTime, dateTimeNow),
                    it.categories,
                    it.subCategories,
                    it.isSetToNotify,
                    it.isBookmarked,
                    it.location,
                    it.latitude,
                    it.longitude,
                    it.endTime.isBeforeNow
                )
            }
        }.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
    }

    private fun getRemainingTimeText(endTime: DateTime, startTime: DateTime = DateTime.now()): RemainingTime {
        val weeksBetween = Weeks.weeksBetween(startTime, endTime)
        val daysBetween = Days.daysBetween(startTime, endTime)
        val hoursBetween = Hours.hoursBetween(startTime, endTime)
        val minutesBetween = Minutes.minutesBetween(startTime, endTime)
        val secondsBetween = Seconds.secondsBetween(startTime, endTime)

        val remainingWeeks = weeksBetween.weeks
        val remainingDays = daysBetween.minus(weeksBetween.toStandardDays()).days
        val remainingHours = hoursBetween.minus(daysBetween.toStandardHours()).hours
        val remainingMinutes = minutesBetween.minus(hoursBetween.toStandardMinutes()).minutes
        val remainingSeconds = secondsBetween.minus(minutesBetween.toStandardSeconds()).seconds

        return RemainingTime(
            String.format(countdownFormat, remainingWeeks),
            String.format(countdownFormat, remainingDays),
            String.format(countdownFormat, remainingHours),
            String.format(countdownFormat, remainingMinutes),
            String.format(countdownFormat, remainingSeconds)
        )
    }
}
