package com.faltro.perch.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.faltro.perch.R
import com.faltro.perch.model.SortType
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
    private var sortType: SortType = SortType.BEST
    private lateinit var adapter: MenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        adapter = MenuAdapter(items) {
            val intent = Intent(this, SubmissionActivity::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        return true
    }

    fun updateSortType(menuItem: MenuItem) {
        sortType = SortType.getSortTypeByName(menuItem.title.toString())
        fetchItems()
    }

    private fun fetchItems() = CoroutineScope(Dispatchers.Main).launch {
        val params: Map<String, String> = mapOf(
                Pair("orderBy", sortType.param)
        )

        val data = async(Dispatchers.IO) {
            polyClient.request(params)
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
