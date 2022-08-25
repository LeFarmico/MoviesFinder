package com.lefarmico.moviesfinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.databinding.RateCircleToggleButtonBinding

class RateMovieAdapter : RecyclerView.Adapter<RateMovieAdapter.ViewHolder>() {

    private val rateButtons: BooleanArray = BooleanArray(10)

    class ViewHolder(
        itemsCircleButtonBinding: RateCircleToggleButtonBinding
    ) : RecyclerView.ViewHolder(itemsCircleButtonBinding.root) {
        private val button = itemsCircleButtonBinding.root
        fun bind(number: Int, rated: Boolean) {
            button.textOn = number.toString()
            button.textOff = number.toString()
            button.isChecked = rated
            Log.d("RateButton", "button $number = ${button.isChecked}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RateCircleToggleButtonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position + 1, rateButtons[position])
        (holder.itemView as ToggleButton).setOnClickListener {
            setRate(position + 1)
        }
    }

    override fun getItemCount(): Int = rateButtons.size

    fun setRate(rate: Int) {
        for (i in rateButtons.indices) {
            rateButtons[i] = false
        }
        if (rate <= 10) {
            rateButtons[rate - 1] = true
        }
        Log.d("RateButton", "button $rate = ${rateButtons[rate - 1]}")
        // TODO change it
        notifyDataSetChanged()
    }
}
