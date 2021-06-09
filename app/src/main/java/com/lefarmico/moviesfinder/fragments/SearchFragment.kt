package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.adapters.SearchAdapter
import com.lefarmico.moviesfinder.databinding.FragmentSearchBinding
import com.lefarmico.moviesfinder.viewModels.SearchFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {

    private val TAG = this.javaClass.canonicalName
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    val viewModel: SearchFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchViewBar.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModel.putSearchRequest(query)
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchSubject.onNext(newText)
                    return true
                }
            })
            setOnClickListener {
                isIconified = false
            }
        }
//        viewModel.getSearchRequests()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { items ->
//                binding.requestRecycler.adapter = SearchAdapter {
//                }.apply {
//                    setLastSearchItems(items)
//                }
//            }

        viewModel.searchViewObservable()
            .debounce(1, TimeUnit.SECONDS)
            .filter {
                !it.isNullOrBlank()
            }
            .distinctUntilChanged()
            .flatMap { requestText ->
                viewModel.getSearchRequestResults(requestText)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                Toast.makeText(context, "Calback is ${list.size}", Toast.LENGTH_SHORT).show()
                binding.requestRecycler.apply {
                    adapter = SearchAdapter {
                        (context as MainActivity).viewModel.onItemClick(it)
                    }.apply {
                        setSearchItems(list)
                    }
                }
            }
    }
}
