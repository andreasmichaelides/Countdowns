package com.bitatron.countdowns.features.globalcountdowns.presentation.model

import com.bitatron.countdowns.features.globalcountdowns.presentation.CountdownRecyclerItemType
import com.bitatron.snazzyrecycling.StringIdRecyclerItem

data class UiCountdown(
    override val id: String,
    val name: String,
    val endTime: String,
    val remainingTime: String,
    val categories: List<String>,
    val subCategories: List<String>,
    val isSetToNotify: Boolean,
    val isBookmarked: Boolean
): StringIdRecyclerItem(id, CountdownRecyclerItemType)