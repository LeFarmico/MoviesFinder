package com.lefarmico.moviesfinder.utils

interface IAdapterOnClickListener {

    var onClickEvent: OnClickEvent?

    fun onClickCallback(item: Any, event: OnClickEvent?) {
        if (event != null) {
            event.onClick(item)
        } else {
            return
        }
    }

    fun setOnClickEvent(event: (Any) -> Unit) {
        onClickEvent = object : OnClickEvent {
            override fun onClick(item: Any) {
                event(item)
            }
        }
    }

    interface OnClickEvent {
        fun onClick(item: Any)
    }
}
