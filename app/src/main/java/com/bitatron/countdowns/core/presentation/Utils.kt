package com.bitatron.countdowns.core.presentation

import android.view.View

fun Boolean.toVisibleOrGone(): Int = if (this) View.VISIBLE else View.GONE
fun Boolean.toVisibleOrInvisible(): Int = if (this) View.VISIBLE else View.INVISIBLE