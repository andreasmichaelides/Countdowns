package com.bitatron.countdowns.features.home.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bitatron.countdowns.R
import com.bitatron.countdowns.core.presentation.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigation: Navigation by inject()

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_global_countdowns -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bookmarks -> {
                textMessage.setText(R.string.title_personal)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_user_countdowns -> {
                textMessage.setText(R.string.title_bookmarks)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navigation.setupWithBottomNavigationView(this, navView, R.id.navigationHostFragment)

        textMessage = findViewById(R.id.message)
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
