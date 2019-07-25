package com.faltro.perch.activity.menu

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faltro.perch.R
import com.faltro.perch.activity.model.Submission
import kotlinx.android.synthetic.main.view_list.view.*

class MenuViewHolder(view: View, private val onClick: (Submission) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private var view: View = view
    private var label = view.label

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK!")
    }

    fun bind(submission: Submission) {
        label.text = submission.displayName
        view.setOnClickListener { onClick(submission) }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (Submission) -> Unit) =
                MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_list, parent, false), onClick)
    }

}