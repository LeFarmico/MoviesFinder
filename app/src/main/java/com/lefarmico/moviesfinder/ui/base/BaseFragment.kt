package com.lefarmico.moviesfinder.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {

    lateinit var viewModel: VM
    lateinit var binding: VB

    internal val job = Job()
    internal val fragmentScope = CoroutineScope(Dispatchers.Main + job)

    abstract fun getInjectViewModel(): VM
    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getInjectViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        fragmentScope.coroutineContext.cancelChildren()
    }
}
