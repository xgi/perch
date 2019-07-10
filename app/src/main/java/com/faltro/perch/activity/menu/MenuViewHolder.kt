package com.faltro.perch.activity.menu

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faltro.perch.R
import kotlinx.android.synthetic.main.view_list.view.*

class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private var view: View = view
    private var label = view.label

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK!")
    }

    fun bind(label_text: String) {
        label.text = label_text
    }

    companion object {
        fun create(parent: ViewGroup) =
                MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_list, parent, false))
    }

}