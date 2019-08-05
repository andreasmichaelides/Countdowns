package com.bitatron.countdowns.core.domain

import android.content.res.Resources
import com.bitatron.countdowns.R
import com.bitatron.countdowns.core.domain.mapper.CountdownToUiCountDownMapper
import org.koin.dsl.module

val domainModule = module {
    factory { CountdownToUiCountDownMapper(get<Resources>().getString(R.string.single_countdown_format)) }
}