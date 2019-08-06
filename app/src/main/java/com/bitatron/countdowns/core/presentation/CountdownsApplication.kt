package com.bitatron.countdowns.core.presentation

import android.app.Application
import com.bitatron.countdowns.core.coreModule
import com.bitatron.countdowns.core.domain.domainModule
import com.bitatron.countdowns.features.addcountdown.addCountdownModule
import com.bitatron.countdowns.features.globalcountdowns.globalCountdownsModule
import com.bitatron.countdowns.features.usercountdowns.userCountdownsModule
import com.bitatron.snazzyrecycling.snazzyRecyclingModule
import com.bitatron.statestream.androidModule
import com.bitatron.statestream.loggingModule
import com.bitatron.statestream.shedulersModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountdownsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CountdownsApplication)
            modules(
                listOf(
                    loggingModule,
                    shedulersModule,
                    androidModule,
                    snazzyRecyclingModule,
                    coreModule,
                    domainModule,
                    globalCountdownsModule,
                    userCountdownsModule,
                    addCountdownModule
                )
            )
        }
    }

}