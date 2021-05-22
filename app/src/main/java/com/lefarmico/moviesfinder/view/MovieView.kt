package com.lefarmico.moviesfinder.view

import android.widget.ProgressBar
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.viewModels.MovieFragmentViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

interface MovieView {

    val viewModel: MovieFragmentViewModel

    fun launchAdapter(
        recyclerView: RecyclerView,
        concatAdapterSingle: Single<ConcatAdapter>
    ): Disposable
    fun launchProgressBar(
        progressBar: ProgressBar,
        progressBarBehaviorSubject: BehaviorSubject<Boolean>
    ): Disposable
}
