package com.faltro.perch.activity.menu

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MenuAdapter(private val items: Array<String>, private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<MenuViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
            MenuViewHolder.create(parent, items, onItemClick)

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
}