package com.bitatron.countdowns.features.globalcountdowns.presentation

import android.view.View
import com.bitatron.countdowns.R
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCategory
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiSubCategory
import com.bitatron.snazzyrecycling.CoreViewHolder
import com.bitatron.snazzyrecycling.RecyclerItem
import com.bitatron.snazzyrecycling.RecyclerItemType
import com.bitatron.snazzyrecycling.addViewHolder
import kotlinx.android.synthetic.main.item_category.*

object CategoryRecyclerItemType: RecyclerItemType
object SubCategoryRecyclerItemType: RecyclerItemType

fun addViewHolders() {
    addViewHolder(CategoryRecyclerItemType, R.layout.item_category, ::createCategoryViewHolder)
    addViewHolder(SubCategoryRecyclerItemType, R.layout.item_category, ::createCategoryViewHolder)
}

fun createCategoryViewHolder(containerView: View) = CategoryViewHolder(containerView)

class CategoryViewHolder(containerView: View): CoreViewHolder(containerView) {
    override fun bind(item: RecyclerItem) {
        when (item) {
            is UiCategory -> {
                categoryChip.text = item.name
                categoryChip.isChecked = item.isSelected
            }
            is UiSubCategory -> {
                categoryChip.text = item.name
                categoryChip.isChecked = item.isSelected
            }
        }
    }
}