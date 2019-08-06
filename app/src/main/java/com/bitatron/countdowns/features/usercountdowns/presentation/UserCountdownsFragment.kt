package com.bitatron.countdowns.features.usercountdowns.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bitatron.countdowns.R
import com.bitatron.countdowns.features.addcountdown.presentation.AddCountdownActivity
import com.bitatron.snazzyrecycling.CoreAdapter
import com.bitatron.statestream.presentation.popAll
import kotlinx.android.synthetic.main.fragment_user_countdowns.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserCountdownsFragment : Fragment() {

    private val userCountdownsViewModel: UserCountdownsViewModel by sharedViewModel()
    private val countdownsCoreAdapter: CoreAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userCountdownsViewModel.activityUiModel().observe(this, Observer { onStateChanged(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_countdowns, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userCountdownsRecycler.adapter = countdownsCoreAdapter
        userCountdownsViewModel.loadData()
    }

    private fun onStateChanged(uiModel: UserCountdownsUiModel) {
        countdownsCoreAdapter.setData(uiModel.countDowns)

        uiModel.activityActions.popAll {
            when(it) {
                is EditCountdownActivityAction -> startActivity(AddCountdownActivity.createActivityIntent(requireActivity()))
            }
        }
    }
}