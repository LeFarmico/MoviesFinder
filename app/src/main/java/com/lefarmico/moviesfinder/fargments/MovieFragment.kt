
package com.lefarmico.moviesfinder.fargments

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
import com.lefarmico.moviesfinder.models.MovieMenuModel
import com.lefarmico.moviesfinder.presenters.MenuPresenter
import com.lefarmico.moviesfinder.presenters.MovieMenuPresenter

class MovieFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var _binding: FragmentItemsBinding? = null
    private val model: MovieMenuModel = MovieMenuModel()

    lateinit var presenter: MenuPresenter

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

        presenter = MovieMenuPresenter(this, model)
        val viewPool = RecyclerView.RecycledViewPool()
        viewPool.setMaxRecycledViews(R.layout.item_placeholder_recycler, 5)
        recyclerView = binding.recyclerParent
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.apply {
            adapter = ConcatAdapter()
            setRecycledViewPool(viewPool)
            // TODO : Добавить items и лямбду на onClick()
        }
        presenter.setItemsData()
    }
}
