package com.bitatron.countdowns.features.globalcountdowns.presentation.model

import com.bitatron.countdowns.features.globalcountdowns.presentation.SubCategoryRecyclerItemType
import com.bitatron.snazzyrecycling.StringIdRecyclerItem

data class UiSubCategory(
    override val id: String,
    val categoryId: String,
    val name: String,
    val isSelected: Boolean
): StringIdRecyclerItem(id, SubCategoryRecyclerItemType)