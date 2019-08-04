package com.faltro.perch.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.faltro.perch.R
import com.faltro.perch.model.Submission
import com.faltro.perch.net.PolyClient
import com.faltro.perch.view.MenuAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement


class MainActivity : AppCompatActivity() {
    companion object {
        const val FIELD_SUBMISSION: String = "submission"
    }


    private val polyClient: PolyClient = PolyClient()
    private val items: ArrayList<Submission> = arrayListOf()
    private lateinit var adapter: MenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MenuAdapter(items) {
            val intent = Intent(this, SceneformActivity::class.java)
            intent.putExtra(FIELD_SUBMISSION, it)
            startActivity(intent)
        }
        recycler_view.adapter = adapter

        swipe_layout.setOnRefreshListener {
            fetchItems()
        }

        // get initial items
        fetchItems()
    }

    private fun fetchItems() = CoroutineScope(Dispatchers.Main).launch {
        val data = async(Dispatchers.IO) {
            polyClient.request()
        }

        val ele: JsonElement = Json.unquoted.parseJson(data.await())
        val assets: JsonArray = ele.jsonObject.getArray("assets")

        items.clear()
        for (asset in assets) {
            val submission = Submission(asset.jsonObject)
            items.add(submission)
        }

        adapter.notifyDataSetChanged()
        swipe_layout.isRefreshing = false
    }
}
