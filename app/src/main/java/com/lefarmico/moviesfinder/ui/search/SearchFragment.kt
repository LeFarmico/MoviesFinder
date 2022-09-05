package com.lefarmico.moviesfinder.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.databinding.FragmentSearchBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override fun getInjectViewModel(): SearchViewModel {
        val viewModel: SearchViewModel by viewModels()
        return viewModel
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

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
