package com.lefarmico.moviesfinder.ui.base

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.lefarmico.moviesfinder.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class BaseActivity <VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var binding: VB

    private val job = Job()
    internal val activityScope = CoroutineScope(Dispatchers.Main + job)

    abstract fun getViewBinding(): VB
    abstract fun getInjectViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        viewModel = getInjectViewModel()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.coroutineContext.cancelChildren()
    }

    fun showAlert(
        message: String,
        actionButtonTitle: String = getString(R.string.okay),
        isCancellable: Boolean = true,
        showCancel: Boolean = true,
        onSuccess: () -> Unit
    ) {
        try {
            val builder1 = AlertDialog.Builder(this)
            builder1.setMessage(message)
            builder1.setTitle(getString(R.string.app_name))
            builder1.setCancelable(isCancellable)
            builder1.setPositiveButton(actionButtonTitle) { dialog, id ->
                onSuccess()
            }
            if (showCancel) {
                builder1.setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog, id ->
                    dialog.cancel()
                }
            }
            val alert11 = builder1.create()
            alert11.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
