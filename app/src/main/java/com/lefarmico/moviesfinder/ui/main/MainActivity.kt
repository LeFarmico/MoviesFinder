package com.lefarmico.moviesfinder.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.ui.base.BaseActivity
import com.lefarmico.moviesfinder.ui.navigation.api.Router
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

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        router.apply {
            bind(this@MainActivity)
            bindNavController(navHostFragment.navController)
            bindNavigationUI(binding.bottomNavigationBarView)
        }

//        appBarConfig = AppBarConfiguration(
//            setOf(R.id.home_fragment, R.id.favorites_menu)
//        )

        viewModel.state.observe(this) { state ->
            state.apply {
                toast?.let {
                    showToast(it)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show().also {
            viewModel.cleanToast()
        }
    }
}
