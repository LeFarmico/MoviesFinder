package com.lefarmico.moviesfinder.view

import androidx.recyclerview.widget.ConcatAdapter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

interface MovieViewModel {

    val concatAdapterObserver: Single<ConcatAdapter>
    val progressBar: BehaviorSubject<Boolean>
}
