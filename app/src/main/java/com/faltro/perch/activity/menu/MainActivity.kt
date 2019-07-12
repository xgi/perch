package com.faltro.perch.activity.menu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.faltro.perch.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val items: ArrayList<String> = arrayListOf()
    private lateinit var adapter: MenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MenuAdapter(items)
        recycler_view.adapter = adapter

        swipeContainer.setOnRefreshListener {
            fetchItems()
        }
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    private fun fetchItems() {
        items.add("a")
        items.add("b")
        items.add("c")
        adapter.notifyDataSetChanged()
        swipeContainer.isRefreshing = false
    }
}
