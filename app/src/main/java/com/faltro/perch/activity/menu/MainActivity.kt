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

        test()
        test()
        test()
    }

    private fun test() {
        items.add("apple")

        Thread.sleep(1000)

        adapter.notifyItemInserted(items.size)
    }
}
