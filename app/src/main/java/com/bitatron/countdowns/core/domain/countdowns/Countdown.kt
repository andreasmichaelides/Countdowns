package com.bitatron.countdowns.core.domain.countdowns

import org.joda.time.DateTime

data class Countdown(
    val id: String,
    val name: String,
    val endTime: DateTime,
    val isSetToNotify: Boolean,
    val isBookmarked: Boolean,
    val categories: List<String>,
    val subCategories: List<String>,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val isUserCountdown: Boolean
)