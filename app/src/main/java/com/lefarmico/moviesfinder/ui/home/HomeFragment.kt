
package com.lefarmico.moviesfinder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.databinding.FragmentHomeBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter.FragmentDestination
import com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter.FragmentRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    @Inject lateinit var fragmentRouter: FragmentRouter

    override fun getInjectViewModel(): HomeViewModel {
        val viewModel: HomeViewModel by viewModels()
        return viewModel
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentRouter.bindFragmentManager(childFragmentManager)

        fragmentRouter.navigate(
            FragmentDestination.Movies
        )
    }
}
