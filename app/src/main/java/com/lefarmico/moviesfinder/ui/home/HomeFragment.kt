
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
        parentJob = job
    ) {
        router.navigate(
            ScreenDestination.FromHomeToMovieDestination,
            MovieFragmentParams(it.movieId)
        )
//        requireActivity().supportFragmentManager.beginTransaction()
//            .addToBackStack("MovieFragment")
//            .add(
//                R.id.nav_host_fragment,
//                MovieFragment::class.java,
//                MovieFragment.createBundle(it.movieId)
//            ).commit()
//        viewModel.showMovieDetail(it.movieId)
    }
    private lateinit var paddingDecorator: PaddingItemDecoration

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

        paddingDecorator = PaddingItemDecoration(
            topPd = requireContext().resources.getDimension(R.dimen.stnd_small_margin).toInt()
        )
        binding.recyclerParent.apply {
            adapter = itemAdapter
            addItemDecoration(paddingDecorator, 0)
        }
        binding.recyclerParent.adapter = itemAdapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            itemAdapter.submitList(state.menuItemList)
        }
    }
}
