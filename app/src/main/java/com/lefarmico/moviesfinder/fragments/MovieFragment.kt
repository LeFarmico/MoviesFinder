
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.viewModels.MovieFragmentViewModel

class MovieFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    val viewModel: MovieFragmentViewModel by viewModels()
    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
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
        setRecyclerViewParams()

        viewModel.progressBarState.observe(viewLifecycleOwner) {
            val progressBar = binding.movieFragment.findViewById(R.id.progress_bar) as ProgressBar
            progressBar.isVisible = it
        }
        viewModel.concatAdapterLiveData.observe(viewLifecycleOwner) {
            recyclerView.adapter = it
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

    private fun setRecyclerViewParams() {
        recyclerView = binding.movieFragment.findViewById(R.id.recycler_parent)
        recyclerView.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext())
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }
}
