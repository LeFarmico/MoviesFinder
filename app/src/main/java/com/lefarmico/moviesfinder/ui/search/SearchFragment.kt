package com.lefarmico.moviesfinder.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var _binding: FragmentSearchBinding
    private val binding get() = _binding

    val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.searchViewBar.apply {
//            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    if (!query.isNullOrBlank()) {
//                        viewModel.putSearchRequest(query)
//                    }
//                    return true
//                }
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    viewModel.searchSubject.onNext(newText)
//                    return true
//                }
//            })
//            setOnClickListener {
//                isIconified = false
//            }
//        }
//
//        viewModel.searchAdapterLiveData.observe(viewLifecycleOwner) { adapter ->
//            adapter.setOnClickEvent {
//                (requireContext() as MainActivity).viewModel.onItemClick(it.itemHeader)
//            }
//            binding.requestRecycler.adapter = adapter
//        }
//    }
}
