package com.android.plottest.ui.customViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.plottest.databinding.CustomBottomSheetBinding
import com.android.plottest.repository.PlotModel
import com.android.plottest.ui.PlotAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.android.plottest.viewmodels.MapViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomBottomSheet(private val itemPressed: (item: PlotModel) -> Unit) :
    BottomSheetDialogFragment() {

    private lateinit var plotAdapter: PlotAdapter
    private lateinit var binding: CustomBottomSheetBinding
    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        bindData()

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAllPlots()
        }
    }

    private fun bindData() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiStateFlowPlots.collect { plots ->
                plotAdapter.submitList(plots)
            }
        }
    }

    private fun initAdapter() {
        plotAdapter = PlotAdapter {
            itemPressed(it)
            dismiss()
        }

        binding.rvPlots.adapter = plotAdapter

    }

    override fun onStart() {
        super.onStart()

        dialog?.let { dialog ->

            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            behavior.skipCollapsed = true
        }
    }

}


