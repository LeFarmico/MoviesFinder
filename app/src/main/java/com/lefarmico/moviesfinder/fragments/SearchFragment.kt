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
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.adapters.ItemAdapter
import com.lefarmico.moviesfinder.databinding.FragmentSearchBinding
import com.lefarmico.moviesfinder.viewModels.SearchFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {

    private val TAG = this.javaClass.canonicalName
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var compositeDisposable = CompositeDisposable()
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

        binding.root.findViewById<SearchView>(R.id.search_view_bar).setOnClickListener {
            (it as SearchView).isIconified = false
        }

        searchViewObservable()
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
            .subscribe {
                Toast.makeText(context, "Calback is ${it.size}", Toast.LENGTH_SHORT).show()
                binding.itemsRecycler.apply {
                    adapter = ItemAdapter {
                        (context as MainActivity).viewModel.onItemClick(it)
                    }.apply {
                        setItems(it)
                    }
                }
            }
    }

    private fun searchViewObservable(): Observable<String> {
        return Observable.create { input ->
            binding.searchViewBar
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        input.onNext(newText)
                        return true
                    }
                })
        }
    }
}
