package com.bitatron.countdowns.features.addcountdown.presentation

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bitatron.countdowns.R
import com.bitatron.statestream.presentation.popAll
import com.google.android.material.picker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_add_countdown.*
import org.joda.time.DateTime
import org.koin.android.ext.android.inject

class AddCountdownActivity : AppCompatActivity() {

    companion object {
        fun createActivityIntent(activity: Activity): Intent {
            return Intent(activity, AddCountdownActivity::class.java)
        }
    }

    private val addCountdownViewModel: AddCountdownViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_countdown)

        addCountdownViewModel.activityUiModel().observe(this, Observer { onStateChanged(it) })

        addCountdownEndDateClickLayout.setOnClickListener { showDatePicker() }
        addCountdownEndTimeClickLayout.setOnClickListener {
            TimePickerDialog(AddCountdownActivity@this, {timePicker, hour, minutes ->
                addCountdownEndTime.setText("$hour:$minutes")
            }, 0, 0, true).show()
        }
    }

    private fun onStateChanged(uiModel: AddCountdownUiModel) {

        uiModel.activityActions.popAll {

        }
    }

    private fun showDatePicker() {

        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .build()

        datePicker.addOnPositiveButtonClickListener {
                val dateTime = DateTime(it)
                addCountdownEndDate.setText(dateTime.dayOfMonth.toString())
            }

        datePicker.show(supportFragmentManager, "DatePickeraki")
    }
}