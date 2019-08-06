package com.faltro.perch.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faltro.perch.R
import com.faltro.perch.model.Submission
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_list.view.*

class MenuViewHolder(view: View, private val onClick: (Submission) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private var view: View = view

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK!")
    }

    fun bind(submission: Submission) {
        view.label.text = submission.displayName
        view.subtitle.text = submission.authorName
        Picasso.with(view.context).load(submission.thumbnailUrl).into(view.thumbnail)
        view.setOnClickListener { onClick(submission) }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (Submission) -> Unit) =
                MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_list, parent, false), onClick)
    }

}