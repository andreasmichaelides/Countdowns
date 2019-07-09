package com.bitatron.countdowns.features.globalcountdowns.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bitatron.countdowns.R
import com.bitatron.snazzyrecycling.AsyncCoreAdapter
import com.bitatron.snazzyrecycling.CoreAdapter
import com.bitatron.snazzyrecycling.ItemOffsetDecoration
import com.bitatron.statestream.presentation.popAll
import kotlinx.android.synthetic.main.fragment_global_countdowns.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GlobalCountdownsFragment : Fragment() {

    private val globalCountdownsViewModel: GlobalCountdownsViewModel by sharedViewModel()
    private val categoriesCoreAdapter: CoreAdapter by inject()
    private val subCategoriesCoreAdapter: CoreAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globalCountdownsViewModel.activityUiModel().observe(this, Observer { onStateChanged(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_global_countdowns, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        categoriesRecycler.adapter = categoriesCoreAdapter
        subCategoriesRecycler.adapter = subCategoriesCoreAdapter
        val itemOffsetDecoration = ItemOffsetDecoration(requireActivity(), R.dimen.recycler_item_spacing)
        categoriesRecycler.addItemDecoration(itemOffsetDecoration)
        subCategoriesRecycler.addItemDecoration(itemOffsetDecoration)

        globalCountdownsViewModel.loadCategories()
    }

    private fun onStateChanged(uiModel: GlobalCountdownsUiModel) {
        categoriesCoreAdapter.setData(uiModel.categories)
        subCategoriesCoreAdapter.setData(uiModel.subCategoriesToDisplay)

//        subCategoriesRecycler.visibility = uiModel.subCategoriesToDisplay.isEmpty()

        uiModel.activityActions.popAll {

        }
    }
}