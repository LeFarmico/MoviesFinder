package com.lefarmico.moviesfinder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private lateinit var movieItem: MovieItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieDetails()

        fragment_favorites_fab.setOnClickListener {
            if (!movieItem.isFavorite){
                fragment_favorites_fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                movieItem.isFavorite = true
            }else{
                fragment_favorites_fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                movieItem.isFavorite = false
            }
        }

        fragment_share_fab.setOnClickListener {
            //создаем интент
            val intent = Intent()
            //Указываем action
            intent.action = Intent.ACTION_SEND
            //Передаем данные
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this movie: ${movieItem.title}"
            )
            //УКазываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            //Запускаем активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }
    private fun setMovieDetails(){
        movieItem = arguments?.get("movie") as MovieItem

        fragment_details_poster.setImageResource(movieItem.poster)
        fragment_details_toolbar.title = movieItem.title
        fragment_favorites_fab.setImageResource(
            if(movieItem.isFavorite) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )

    }
}