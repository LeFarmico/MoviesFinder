package com.lefarmico.moviesfinder.ui.home

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.data.manager.useCase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel()
