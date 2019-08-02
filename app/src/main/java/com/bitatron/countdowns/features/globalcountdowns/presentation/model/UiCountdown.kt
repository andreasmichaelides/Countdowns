package com.bitatron.countdowns.features.globalcountdowns.presentation.model

import com.bitatron.countdowns.features.globalcountdowns.presentation.CountdownRecyclerItemType
import com.bitatron.snazzyrecycling.StringIdRecyclerItem

data class UiCountdown(
    override val id: String,
    val name: String,
    val endTime: String,
    val remainingTime: RemainingTime,
    val categories: List<String>,
    val subCategories: List<String>,
    val isSetToNotify: Boolean,
    val isBookmarked: Boolean,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val ended: Boolean
): StringIdRecyclerItem(id, CountdownRecyclerItemType)

data class RemainingTime(
    val weeks: String,
    val days: String,
    val hours: String,
    val minutes: String,
    val seconds: String
)