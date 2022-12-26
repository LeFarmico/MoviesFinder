package com.lefarmico.moviesfinder.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router.bind(this@MainActivity)

        // TODO create only once
//        router.navigate(ScreenDestination.Home)

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
