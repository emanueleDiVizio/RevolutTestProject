package com.example.revolut_test.utils

import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.listenForOnClick(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}