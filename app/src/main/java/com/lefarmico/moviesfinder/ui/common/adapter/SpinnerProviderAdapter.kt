package com.lefarmico.moviesfinder.ui.common.adapter

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieProviderData
import com.lefarmico.moviesfinder.private.Private
import com.squareup.picasso.Picasso

class SpinnerProviderAdapter(
    context: Context,
    movieProviderData: List<MovieProviderData>
) : ArrayAdapter<MovieProviderData>(context, R.layout.item_provider_spinner_default, movieProviderData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item_provider, parent, false)
        if (position == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_provider_spinner_default, parent, false)
        }
        getItem(position)?.let { provider ->
            setProviderItem(view, provider)
        }
        return view
    }

    override fun isEnabled(position: Int): Boolean = position != 0

    override fun getCount(): Int {
        return super.getCount() + 1
    }

    override fun getItem(position: Int): MovieProviderData? {
        if (position == 0) return null
        return super.getItem(position - 1)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_provider_spinner_default, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item_provider, parent, false)

            getItem(position)?.let { provider ->
                setProviderItem(view, provider)
            }
        }
        return view
    }

    private fun setProviderItem(view: View, movieProviderData: MovieProviderData) {
        val imageProvider = view.findViewById<ImageView>(R.id.provider_image_view)
        val textProvider = view.findViewById<TextView>(R.id.provider_title_text_view)

        Picasso
            .get()
            .load(Private.IMAGES_URL + "w92" + movieProviderData.logoPath)
            .fit()
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageProvider)
        textProvider.text = movieProviderData.name
    }
}
