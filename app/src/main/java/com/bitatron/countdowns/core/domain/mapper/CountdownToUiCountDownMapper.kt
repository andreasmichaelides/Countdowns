package com.bitatron.countdowns.core.domain.mapper

import com.bitatron.countdowns.core.domain.countdowns.Countdown
import com.bitatron.countdowns.core.domain.utils.getRemainingTimeText
import com.bitatron.countdowns.core.presentation.model.UiCountdown
import org.joda.time.DateTime

class CountdownToUiCountDownMapper(private val countdownFormat: String) {

    fun map(it: Countdown, dateTimeNow: DateTime = DateTime.now()): UiCountdown {
        return UiCountdown(
            it.id,
            it.name,
            it.endTime.toString(),
            getRemainingTimeText(it.endTime, dateTimeNow, countdownFormat),
            it.categories,
            it.subCategories,
            it.isSetToNotify,
            it.isBookmarked,
            it.location,
            it.latitude,
            it.longitude,
            it.endTime.isBeforeNow,
            it.isUserCountdown
        )
    }

}