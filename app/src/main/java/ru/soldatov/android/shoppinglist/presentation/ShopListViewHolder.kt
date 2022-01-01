package ru.soldatov.android.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.soldatov.android.shoppinglist.R

class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tv_name)
    val tvCount = itemView.findViewById<TextView>(R.id.tv_count)
}