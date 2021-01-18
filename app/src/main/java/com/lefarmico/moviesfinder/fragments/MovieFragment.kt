
package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentItemsBinding
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
        createPresenter()

        val viewPool = RecyclerView.RecycledViewPool()
        viewPool.setMaxRecycledViews(R.layout.item_placeholder_recycler, 5)
        recyclerView = binding.recyclerParent
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
}
