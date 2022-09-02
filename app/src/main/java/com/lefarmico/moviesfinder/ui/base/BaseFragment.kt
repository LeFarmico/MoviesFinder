package com.lefarmico.moviesfinder.ui.base

import android.os.Bundle
import android.util.Log
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

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    abstract fun getInjectViewModel(): VM
    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugLog { "onCreate()" }
        viewModel = getInjectViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        debugLog { "onCreateView()" }
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private inline fun debugLog(logText: () -> String) {
        val className = this.javaClass.name
        Log.d(className, logText.invoke())
    }
}
