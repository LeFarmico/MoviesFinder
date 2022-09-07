package com.lefarmico.moviesfinder.data.manager

import android.util.Log
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import javax.inject.Inject

class Interactor @Inject constructor() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val showMovieChannel = Channel<MovieDetailedData>(Channel.CONFLATED).apply {
        invokeOnClose {
            Log.e("Interactor", "Channel is closed", it)
        }
    }

    suspend fun sendMovieDetailedToChannel(movieDetailedData: MovieDetailedData) {
        showMovieChannel.send(movieDetailedData)
    }

    suspend fun actionOnMovieDetailedInvoked(action: (MovieDetailedData) -> Unit) {
        showMovieChannel.consumeEach {
            action(it)
        }
    }
}
