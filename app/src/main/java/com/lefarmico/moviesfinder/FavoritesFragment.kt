package com.lefarmico.moviesfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lefarmico.moviesfinder.adapterDelegates.ContainerAdapter
import com.lefarmico.moviesfinder.adapterDelegates.ItemAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import com.lefarmico.moviesfinder.decorators.HeaderItemDecorator
import com.lefarmico.moviesfinder.decorators.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_favorites.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoritesList: List<MovieItem> = emptyList()

        favorites_recycler.apply {
            adapter = ItemAdapter(object : ItemAdapter.OnItemClickListener{
                override fun click(item: Item) {
                    if(item is MovieItem)
                        (requireActivity() as MainActivity).launchDetailsFragment(item)
                    else return
                }
            })
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

            layoutManager = LinearLayoutManager(requireContext())

        }
    }
}