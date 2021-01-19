
package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.transition.Scene
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentItemsBinding
import com.lefarmico.moviesfinder.databinding.MergeMovieScreenContentBinding
import com.lefarmico.moviesfinder.presenters.ItemsPresenter

class MovieFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var _binding: FragmentItemsBinding? = null

    lateinit var itemsPresenter: ItemsPresenter

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAppAnimation()
        createPresenter()
        val viewPool = RecyclerView.RecycledViewPool()
        viewPool.setMaxRecycledViews(R.layout.item_placeholder_recycler, 5)
        recyclerView = binding.mergeMovieScreenContent.findViewById(R.id.recycler_parent)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.apply {
            adapter = ConcatAdapter()
            setRecycledViewPool(viewPool)
        }
        itemsPresenter.loadData()
    }

    // TODO : Вынести в фоновый класс
    private fun createPresenter() {
        itemsPresenter = ItemsPresenter(this)
    }

    private fun initToolsBar() {
        binding.root.findViewById<SearchView>(R.id.search_view_bar).setOnClickListener {
            (it as SearchView).isIconified = false
        }
        binding.root.findViewById<SearchView>(R.id.search_view_bar).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
    private fun startAppAnimation() {
        val scene = Scene.getSceneForLayout(binding.mergeMovieScreenContent, R.layout.merge_movie_screen_content, requireContext())
        val searchSlide = Slide(Gravity.TOP).addTarget(R.id.search_view_bar)
        val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(R.id.recycler_parent)
        val customTransition = TransitionSet().apply {
            duration = 500
            addTransition(recyclerSlide)
            addTransition(searchSlide)
        }
        TransitionManager.go(scene, customTransition)
    }
}
