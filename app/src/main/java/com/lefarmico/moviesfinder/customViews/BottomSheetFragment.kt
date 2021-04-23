package com.lefarmico.moviesfinder.customViews

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieDetailsBinding

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
    }
}
