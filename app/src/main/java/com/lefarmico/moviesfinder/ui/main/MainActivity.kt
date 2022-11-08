package com.lefarmico.moviesfinder.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.ui.base.BaseActivity
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var router: Router

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getInjectViewModel(): MainViewModel {
        val viewModel: MainViewModel by viewModels()
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router.bind(this@MainActivity)

        // TODO create only once
        router.navigate(ScreenDestination.Home)

        viewModel.state.observe(this) { state ->
            state.apply {
                toast?.let {
                    showToast(it)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return supportFragmentManager.backStackEntryCount == 1
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show().also {
            viewModel.cleanToast()
        }
    }
}
