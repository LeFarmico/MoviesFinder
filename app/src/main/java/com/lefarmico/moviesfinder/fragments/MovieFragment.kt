
package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.transition.Fade
import android.transition.Scene
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.view.MovieView
import com.lefarmico.moviesfinder.viewModels.MovieFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MovieFragment : Fragment(), MovieView {

    lateinit var recyclerView: RecyclerView
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override val viewModel: MovieFragmentViewModel by viewModels()
    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate ${this.hashCode()}")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        startFragmentAnimation()
        launchRecyclerViewParams()

        launchProgressBar(
            binding.movieFragment.findViewById(R.id.progress_bar),
            viewModel.progressBar
        )
        launchAdapter(recyclerView, viewModel.concatAdapterData)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    override fun launchAdapter(
        recyclerView: RecyclerView,
        concatAdapterSingle: Single<ConcatAdapter>
    ): Disposable {
        return concatAdapterSingle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { recyclerView.adapter = it },
                { throw (NullPointerException()) }
            )
    }
    override fun launchProgressBar(
        progressBar: ProgressBar,
        progressBarBehaviorSubject: BehaviorSubject<Boolean>
    ): Disposable {
        return progressBarBehaviorSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                progressBar.isVisible = it
            }
    }

    private fun startFragmentAnimation() {
        val scene = Scene.getSceneForLayout(
            binding.movieFragment,
            R.layout.merge_fragment_movie,
            requireContext()
        )
        val recyclerFade = Fade(Fade.MODE_IN).addTarget(R.id.recycler_parent)
        val customTransition = TransitionSet().apply {
            duration = 1000
            addTransition(recyclerFade)
        }
        TransitionManager.go(scene, customTransition)
    }

    private fun launchRecyclerViewParams() {
        recyclerView = binding.movieFragment.findViewById(R.id.recycler_parent)
        recyclerView.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext())
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }
}
