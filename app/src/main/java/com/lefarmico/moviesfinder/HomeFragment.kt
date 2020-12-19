package com.lefarmico.moviesfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.adapterDelegates.ContainerAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDelegatesRecycler()
    }

    private fun initDelegatesRecycler(){
        recyclerView = recycler_parent
        recyclerView.apply {
            adapter = ContainerAdapter(object : ContainerAdapter.OnContainerClickListener{
                override fun click(item: Item) {
                    if(item is MovieItem)
                        (requireActivity() as MainActivity).launchDetailsFragment(item)
                    else return
                }
            })
            layoutManager = LinearLayoutManager(requireContext())
            (adapter as ContainerAdapter).items = ContainerDataFactory().getContainerModel(10, 10)
        }
    }
}