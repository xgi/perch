package com.faltro.perch.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
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
import kotlinx.serialization.json.content


class MainActivity : AppCompatActivity() {
    companion object {
        const val FIELD_SUBMISSION: String = "submission"
    }


    private val polyClient: PolyClient = PolyClient()
    private val items: ArrayList<Submission> = arrayListOf()
    private var sortType: SortType = SortType.BEST
    private lateinit var adapter: MenuAdapter

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 8
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var nextPageToken: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layoutManager

        adapter = MenuAdapter(items) {
            val intent = Intent(this, SubmissionActivity::class.java)
            intent.putExtra(FIELD_SUBMISSION, it)
            startActivity(intent)
        }
        recycler_view.adapter = adapter

        swipe_layout.setOnRefreshListener {
            items.clear()
            fetchItems()
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                visibleItemCount = recycler_view.childCount
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    loading = true

                    if (nextPageToken != "") {
                        fetchItems(nextPageToken)
                    } else {
                        fetchItems()
                    }

                }
            }
        })

        // get initial items
        fetchItems()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        return true
    }

    fun updateSortType(menuItem: MenuItem) {
        sortType = SortType.getSortTypeByName(menuItem.title.toString())
        items.clear()
        fetchItems()
    }

    private fun fetchItems(pageToken: String = "") = CoroutineScope(Dispatchers.Main).launch {
        val params: MutableMap<String, String> = mutableMapOf(
                Pair("orderBy", sortType.param)
        )
        if (pageToken != "") params["pageToken"] = pageToken

        val data = async(Dispatchers.IO) {
            polyClient.request(params)
        }

        val ele: JsonElement = Json.unquoted.parseJson(data.await())
        val assets: JsonArray = ele.jsonObject.getArray("assets")
        nextPageToken = ele.jsonObject["nextPageToken"]?.content ?: ""

        for (asset in assets) {
            val submission = Submission(asset.jsonObject)
            items.add(submission)
        }

        adapter.notifyDataSetChanged()
        swipe_layout.isRefreshing = false
    }
}
