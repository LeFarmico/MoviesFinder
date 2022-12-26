package com.lefarmico.moviesfinder.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentMoviesBinding
import com.lefarmico.moviesfinder.ui.common.adapter.MenuItemAdapter
import com.lefarmico.moviesfinder.ui.common.decorator.PaddingItemDecoration
import com.lefarmico.moviesfinder.ui.delegation.lifecycle.LifecycleScopeDelegation
import com.lefarmico.moviesfinder.ui.delegation.lifecycle.LifecycleScopeDelegationImpl
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.params.MovieFragmentParams
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
class Movies :
    Fragment(),
    LifecycleScopeDelegation by LifecycleScopeDelegationImpl() {

    private lateinit var _binding: FragmentMoviesBinding
    private val binding get() = _binding

    @Inject
    lateinit var router: Router

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var itemAdapter: MenuItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerScope(lifecycle)

        itemAdapter = MenuItemAdapter(
            parentJob = fragmentJob,
            onMovieClick = {
                router.navigate(
                    ScreenDestination.FromHomeToMovie,
                    MovieFragmentParams(it.movieId)
                )
            },
            onErrorAction = {
                viewModel.setErrorState(it)
            }
        )

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
                    errorText.text = getString(state.error.title)
                    errorDescription.text = getString(state.error.description)
                    errorButton.text = getString(state.error.buttonDescription)
                }
                binding.errorLayout.errorButton.setOnClickListener {
                    viewModel.loadMoviesCategories()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentScope.cancel()
    }
}
