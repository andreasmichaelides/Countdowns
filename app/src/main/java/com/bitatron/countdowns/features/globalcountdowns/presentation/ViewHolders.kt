package com.bitatron.countdowns.features.globalcountdowns.presentation

import android.view.View
import com.bitatron.countdowns.R
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCategory
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiCountdown
import com.bitatron.countdowns.features.globalcountdowns.presentation.model.UiSubCategory
import com.bitatron.snazzyrecycling.*
import kotlinx.android.synthetic.main.item_category.*
import kotlinx.android.synthetic.main.item_countdown.*

object CategoryRecyclerItemType: RecyclerItemType
object SubCategoryRecyclerItemType: RecyclerItemType
object CountdownRecyclerItemType: RecyclerItemType

data class CategoryClickAction(val isChecked: Boolean): ClickAction
data class SubCategoryClickAction(val isChecked: Boolean): ClickAction

fun addViewHolders() {
    addViewHolder(CategoryRecyclerItemType, R.layout.item_category, ::createCategoryViewHolder)
    addViewHolder(SubCategoryRecyclerItemType, R.layout.item_category, ::createSubCategoryViewHolder)
    addViewHolder(CountdownRecyclerItemType, R.layout.item_countdown, ::createCountdownViewHolder)
}

fun createCategoryViewHolder(containerView: View) = CategoryViewHolder(containerView)
fun createSubCategoryViewHolder(containerView: View) = SubCategoryViewHolder(containerView)
fun createCountdownViewHolder(containerView: View) = CountdownViewHolder(containerView)

class CategoryViewHolder(containerView: View): ClickableCoreViewHolder(containerView) {

    init {
        categoryChip.setOnClickListener{ itemClicked.onNext(ClickedRecyclerItem(CategoryClickAction(categoryChip.isChecked), item)) }
    }

    override fun bind(item: RecyclerItem) {
        super.bind(item)
        when (item) {
            is UiCategory -> {
                categoryChip.text = item.name
                categoryChip.isChecked = item.isSelected
            }
        }
    }
}

class SubCategoryViewHolder(containerView: View): ClickableCoreViewHolder(containerView) {

    init {
        categoryChip.setOnClickListener{ itemClicked.onNext(ClickedRecyclerItem(SubCategoryClickAction(categoryChip.isChecked), item)) }
    }

    override fun bind(item: RecyclerItem) {
        super.bind(item)
        when (item) {
            is UiSubCategory -> {
                categoryChip.text = item.name
                categoryChip.isChecked = item.isSelected
            }
        }
    }
}

class CountdownViewHolder(containerView: View): ClickableCoreViewHolder(containerView) {

    init {
//        categoryChip.setOnClickListener{ itemClicked.onNext(ClickedRecyclerItem(SubCategoryClickAction(categoryChip.isChecked), item)) }
    }

    override fun bind(item: RecyclerItem) {
        super.bind(item)
        when (item) {
            is UiCountdown -> {
                countDownName.text = item.name
                countDownRemainingTime.text = item.remainingTime
            }
        }
    }
}