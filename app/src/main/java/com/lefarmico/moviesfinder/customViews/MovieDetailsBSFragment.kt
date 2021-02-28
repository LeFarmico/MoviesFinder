package com.lefarmico.moviesfinder.customViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lefarmico.moviesfinder.databinding.MovieDetailsBottomSheetBinding

class MovieDetailsBSFragment : BottomSheetDialogFragment() {

    private var _binding: MovieDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }
}
