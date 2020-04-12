package com.example.revolut_test.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency.view.*

fun <T : RecyclerView.ViewHolder> T.listenForOnClick(event: (position: Int, type: Int, view: View) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType, itemView)
    }
    return this
}