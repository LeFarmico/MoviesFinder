
package com.lefarmico.moviesfinder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentHomeBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import com.lefarmico.moviesfinder.ui.common.adapter.MenuItemAdapter
import com.lefarmico.moviesfinder.ui.common.decorator.PaddingItemDecoration
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.params.MovieFragmentParams
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    @Inject lateinit var router: Router

    private var itemAdapter = MenuItemAdapter(
        parentJob = job,
        onMovieClick = {
            router.navigate(
                ScreenDestination.FromHomeToMovieDestination,
                MovieFragmentParams(it.movieId)
            )
        },
        onErrorAction = {
            viewModel.setErrorState(it)
        }
    )

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

        binding.recyclerParent.apply {
            adapter = itemAdapter
            addItemDecoration(
                PaddingItemDecoration(topPd = requireContext().resources.getDimension(R.dimen.stnd_small_margin).toInt())
            )
        }
        binding.recyclerParent.adapter = itemAdapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            itemAdapter.submitList(state.menuItemList)

            // Default State
            binding.errorLayout.root.visibility = View.GONE
            binding.recyclerParent.visibility = View.VISIBLE
            binding.progressBar.isVisible = state.isLoading

            // Loading State
            if (state.isLoading) {
                binding.errorLayout.root.visibility = View.GONE
                binding.recyclerParent.visibility = View.GONE
            }

            // Error State
            if (state.error != null) {
                binding.progressBar.isVisible = false
                binding.recyclerParent.visibility = View.INVISIBLE
                binding.errorLayout.root.visibility = View.VISIBLE
                binding.errorLayout.apply {
                    errorText.text = getString(state.error.errorTitle)
                    errorDescription.text = getString(state.error.errorDescription)
                    errorButton.text = getString(state.error.errorButtonDescription)
                }
                binding.errorLayout.errorButton.setOnClickListener {
                    viewModel.loadMoviesCategories()
                }
            }
        }
    }
}
