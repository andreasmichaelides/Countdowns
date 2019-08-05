package com.bitatron.countdowns.core.presentation

import android.view.View
import com.bitatron.countdowns.R
import com.bitatron.countdowns.core.presentation.model.UiCountdown
import com.bitatron.snazzyrecycling.*
import kotlinx.android.synthetic.main.item_countdown.*

object CountdownNotificationClickAction: ClickAction
object CategoryBookmarkClickAction: ClickAction
object DeleteCountdownClickAction: ClickAction
object EditCountdownClickAction: ClickAction

object CountdownRecyclerItemType: RecyclerItemType

fun addViewHolders() {
    addViewHolder(CountdownRecyclerItemType, R.layout.item_countdown, ::createCountdownViewHolder)
}

fun createCountdownViewHolder(containerView: View) = CountdownViewHolder(containerView)

class CountdownViewHolder(containerView: View): ClickableCoreViewHolder(containerView) {

    init {
        countdownNotification.setOnClickListener{ itemClicked.onNext(
            ClickedRecyclerItem(
                CountdownNotificationClickAction, item)
        ) }
        countdownBookmarked.setOnClickListener{ itemClicked.onNext(ClickedRecyclerItem(CategoryBookmarkClickAction, item)) }
        countdownDelete.setOnClickListener{ itemClicked.onNext(ClickedRecyclerItem(DeleteCountdownClickAction, item)) }
        countdownEdit.setOnClickListener{ itemClicked.onNext(ClickedRecyclerItem(EditCountdownClickAction, item)) }
    }

    override fun bind(item: RecyclerItem) {
        super.bind(item)
        when (item) {
            is UiCountdown -> {
                countDownName.text = item.name

                countdownNumberOfWeeks.text = item.remainingTime.weeks
                countdownNumberOfDays.text = item.remainingTime.days
                countdownNumberOfHours.text = item.remainingTime.hours
                countdownNumberOfMinutes.text = item.remainingTime.minutes
                countdownNumberOfSeconds.text = item.remainingTime.seconds

                countdownTimerLayout.visibility = item.ended.not().toVisibleOrInvisible()
                countdownEndedText.visibility = item.ended.toVisibleOrGone()
                countdownDelete.visibility = item.isUserCountdown.toVisibleOrGone()
                countdownEdit.visibility = item.isUserCountdown.toVisibleOrGone()

                countdownNotification.setImageResource(if (item.isSetToNotify) R.drawable.ic_notifications_active_black_24dp else R.drawable.ic_notifications_none_black_24dp)
                countdownBookmarked.setImageResource(if (item.isBookmarked) R.drawable.ic_bookmark_black_24dp else R.drawable.ic_bookmark_border_black_24dp)
            }
        }
    }
}