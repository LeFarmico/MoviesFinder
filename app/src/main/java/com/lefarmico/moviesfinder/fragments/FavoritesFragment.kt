package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.data.MovieItem
import com.lefarmico.moviesfinder.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoritesList: List<MovieItem> = emptyList()

//        binding.favoritesRecycler.apply {
//            adapter = ItemAdapter(object : ItemAdapter.OnItemClickListener {
//                override fun click(item: Item) {
//                    if (item is MovieItem)
//                        (requireActivity() as MainActivity).launchDetailsFragment(item)
//                    else return
//                }
//            })
//            val decorator = TopSpacingItemDecoration(8)
//            addItemDecoration(decorator)
//
//            layoutManager = LinearLayoutManager(requireContext())
    }
}
