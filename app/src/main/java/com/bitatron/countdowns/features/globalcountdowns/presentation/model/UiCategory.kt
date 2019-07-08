package com.bitatron.countdowns.features.globalcountdowns.presentation.model

import com.bitatron.countdowns.features.globalcountdowns.presentation.CategoryRecyclerItemType
import com.bitatron.snazzyrecycling.StringIdRecyclerItem

data class UiCategory(
    override val id: String,
    val name: String,
    val isSelected: Boolean
): StringIdRecyclerItem(id, CategoryRecyclerItemType)