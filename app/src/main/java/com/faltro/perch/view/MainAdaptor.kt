package com.faltro.perch.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.faltro.perch.model.Submission


class MainAdaptor(private val items: ArrayList<Submission>, private val onItemClick: (Submission) -> Unit) : RecyclerView.Adapter<SubmissionViewHolder>() {
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        return SubmissionViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        holder.bind(items[position])
    }
}