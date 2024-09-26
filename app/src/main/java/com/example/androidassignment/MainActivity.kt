package com.example.androidassignment

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassignment.databinding.AssignViewBinding
import com.example.androidassignment.features.assignment.adapter.ImagePagerAdapter
import com.example.androidassignment.features.assignment.adapter.ListItemAdapter
import com.example.androidassignment.features.assignment.model.AssignmentViewState
import com.example.androidassignment.features.assignment.view.ModalBottomSheetDialog
import com.example.androidassignment.features.assignment.viewModel.AssignmentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: AssignViewBinding
    private val viewModel: AssignmentViewModel by viewModels()
    private val imagePagerAdapter: ImagePagerAdapter = ImagePagerAdapter()
    private val listItemAdapter: ListItemAdapter = ListItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.assign_view)
        setUpViewPager()
        setUpRecyclerView()
        setupSearchView()
        setupFab()
        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                updateUIState(state)
            }
        }
    }

    private fun updateUIState(state: AssignmentViewState) {
        imagePagerAdapter.setImages(state.items.keys.toList())
        state.items[state.selectedImage]?.let { listItemAdapter.setItems(it) }
    }

    private fun setUpViewPager() {
        with(binding) {
            viewPager.adapter = imagePagerAdapter
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewModel.onEvent(AssignmentViewModel.AssignmentUiEvent.OnImageChanged(tab.position))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })

            TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        }
    }


    private fun setUpRecyclerView() {
        with(binding) {
            recyclerView.adapter = listItemAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onEvent(AssignmentViewModel.AssignmentUiEvent.OnQueryChanged(query.orEmpty()))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onEvent(AssignmentViewModel.AssignmentUiEvent.OnQueryChanged(newText.orEmpty()))
                return true
            }
        })
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
           val modal = ModalBottomSheetDialog(viewModel.getTop3Characters())
            supportFragmentManager.let { modal.show(it,ModalBottomSheetDialog.TAG) }

        }
    }

}
