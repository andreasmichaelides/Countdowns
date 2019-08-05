package com.bitatron.countdowns.core.domain.utils

import com.bitatron.countdowns.core.presentation.model.RemainingTime
import org.joda.time.*

fun getRemainingTimeText(endTime: DateTime, startTime: DateTime = DateTime.now(), countdownFormat: String): RemainingTime {
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