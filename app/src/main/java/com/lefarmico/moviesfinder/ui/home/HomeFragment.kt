
package com.lefarmico.moviesfinder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.databinding.FragmentHomeBinding
import com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter.FragmentDestination
import com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter.FragmentRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    val viewModel: HomeViewModel by viewModels()

    @Inject lateinit var fragmentRouter: FragmentRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentRouter.bindFragmentManager(childFragmentManager)

        fragmentRouter.navigate(
            FragmentDestination.Movies
        )
    }
}
