package com.android.plottest.ui.customViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.android.plottest.databinding.CustomAlertDialogBinding

class CustomAlertDialog(val savePolygon: (name: String) -> Unit) : DialogFragment() {

    private lateinit var binding: CustomAlertDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomAlertDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        binding.tvSaveAction.setOnClickListener {
            val name = binding.editTextPolygon.text.toString()
            if (name.isEmpty()) {
                return@setOnClickListener
            }

            savePolygon(name)
            dismiss()
        }

        binding.tvCancelAction.setOnClickListener {
            dismiss()
        }
    }

}