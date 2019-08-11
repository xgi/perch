package com.faltro.perch.view

import android.content.Intent
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.faltro.perch.R
import com.faltro.perch.activity.MainActivity
import com.faltro.perch.activity.SubmissionActivity
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

        view.options.setOnClickListener {
            val popup = PopupMenu(view.context, view.options)
            popup.inflate(R.menu.submission_card_actions)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.view_object -> {
                        val intent = Intent(view.context, SubmissionActivity::class.java)
                        intent.putExtra(MainActivity.FIELD_SUBMISSION, submission)
                        view.context.startActivity(intent)
                    }
                    R.id.hide -> {
                        Toast.makeText(view.context, item.title, Toast.LENGTH_SHORT).show();
                    }
                }
                true
            }
            popup.show()
        }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (Submission) -> Unit) =
                MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_list, parent, false), onClick)
    }

}