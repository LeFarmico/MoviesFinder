package com.lefarmico.moviesfinder.data.manager

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import javax.inject.Inject

class Interactor @Inject constructor() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val showMovieChannel = Channel<Int>(Channel.CONFLATED).apply {
        invokeOnClose {
            Log.e("Interactor", "Channel is closed", it)
        }
    }

    suspend fun sendMovieDetailedToChannel(movieId: Int) {
        showMovieChannel.send(movieId)
    }

    suspend fun actionOnMovieDetailedInvoked(action: suspend (Int) -> Unit) {
        showMovieChannel.consumeEach {
            action(it)
        }
    }
}
