package com.faltro.perch.activity.menu

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MenuAdapter(private val items: ArrayList<String>) : RecyclerView.Adapter<MenuViewHolder>() {
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }
}