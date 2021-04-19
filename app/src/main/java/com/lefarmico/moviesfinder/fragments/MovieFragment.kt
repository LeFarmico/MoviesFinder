
package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.transition.Fade
import android.transition.Scene
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.view.MoviesView
import com.lefarmico.moviesfinder.viewModels.MovieFragmentViewModel
import java.util.*
import javax.inject.Inject

class MovieFragment : Fragment(), MoviesView {

    lateinit var recyclerView: RecyclerView
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var interactor: Interactor
    lateinit var concatAdapter: ConcatAdapter

    private val movieFragmentViewModel: MovieFragmentViewModel by viewModels()
    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        if (this::concatAdapter.isInitialized) {
            concatAdapter.apply {
                adapters.forEach {
                    removeAdapter(it)
                }
            }
        }

        for (i in movieFragmentViewModel.categoriesMobileData.indices) {
            movieFragmentViewModel.categoriesMobileData[i].observe(viewLifecycleOwner) {
                show(it)
            }
        }

        movieFragmentViewModel.showProgressBar.observe(viewLifecycleOwner) {
            binding.mergeMovieScreenContent.findViewById<ProgressBar>(R.id.progress_bar).isVisible = it
        }

        startFragmentAnimation()
        initToolsBar()

        recyclerView = binding.mergeMovieScreenContent.findViewById(R.id.recycler_parent)

        recyclerView.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext())
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }

    private fun initToolsBar() {
        binding.root.findViewById<SearchView>(R.id.search_view_bar).setOnClickListener {
            (it as SearchView).isIconified = false
        }
        binding.root.findViewById<SearchView>(R.id.search_view_bar)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
    }
    private fun startFragmentAnimation() {
        val scene = Scene.getSceneForLayout(
            binding.mergeMovieScreenContent,
            R.layout.merge_fragment_movie,
            requireContext()
        )
        val searchFade = Fade(Fade.MODE_IN).addTarget(R.id.search_view_bar)
        val recyclerFade = Fade(Fade.MODE_IN).addTarget(R.id.recycler_parent)

        val customTransition = TransitionSet().apply {
            duration = 1000
            addTransition(searchFade)
            addTransition(recyclerFade)
        }
        TransitionManager.go(scene, customTransition)
    }

    override fun showCatalog(categories: List<Category>) {
        if (!this::concatAdapter.isInitialized) {
            concatAdapter = ConcatAdapter()
        }
        concatAdapter.apply {
            adapters.forEach {
                removeAdapter(it)
            }
            for (i in categories.indices) {
                val headerAdapter = HeaderAdapter().apply {
                    addItem(context?.getString(categories[i].titleResource)!!)
                }
                concatAdapter.addAdapter(headerAdapter)
                val itemsAdapter = ItemsPlaceholderAdapter(interactor).apply {
                    categories[i].itemsList.observe(viewLifecycleOwner) {
                        setNestedItemsData(it.toMutableList())
                    }
                    categoryType = categories[i].categoryType
                }
                concatAdapter.addAdapter(itemsAdapter)
            }
        }
        recyclerView.apply {
            this.adapter = concatAdapter
        }
    }
    private fun show(category: Category) {
        if (!this::concatAdapter.isInitialized)
            concatAdapter = ConcatAdapter()

        val headerAdapter = HeaderAdapter().apply {
            addItem(context?.getString(category.titleResource)!!)
        }
        concatAdapter.addAdapter(headerAdapter)
        val itemsAdapter = ItemsPlaceholderAdapter(interactor).apply {
            category.itemsList.observe(viewLifecycleOwner) {
                setNestedItemsData(it.toMutableList())
            }
            categoryType = category.categoryType
        }
        concatAdapter.addAdapter(itemsAdapter)
        recyclerView.apply {
            this.adapter = concatAdapter
        }
    }

    override fun showError() {
        recyclerView = binding.mergeMovieScreenContent.findViewById(R.id.recycler_parent)

        recyclerView.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ConcatAdapter()
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }

    override fun showEmptyCatalog() {
        Toast.makeText(requireContext(), "Something had wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onStartLoading() {
    }
}
