package com.faltro.perch.activity.menu

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.faltro.perch.activity.model.Submission


class MenuAdapter(private val items: ArrayList<Submission>, private val onItemClick: (Submission) -> Unit) : RecyclerView.Adapter<MenuViewHolder>() {
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<Submission>) {
        items.addAll(list)
        notifyDataSetChanged()
    }
}