package com.lefarmico.moviesfinder.ui.common.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.ui.common.notification.NotificationConstants.CHANNEL_ID
import com.lefarmico.moviesfinder.ui.main.MainActivity

object NotificationHelper {
    fun createNotification(context: Context, movieItem: MovieDetailedData) {
        val mIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_baseline_movie_24)
            setContentTitle("Не забудьте посмотреть!")
            setContentText(movieItem.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(movieItem.movieId, builder.build())
    }
}
