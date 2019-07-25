package com.faltro.perch.activity.menu

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.faltro.perch.BuildConfig
import com.faltro.perch.R
import com.faltro.perch.activity.SceneformActivity
import com.faltro.perch.activity.model.Submission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import java.net.URL


class MainActivity : AppCompatActivity() {
    private val items: ArrayList<Submission> = arrayListOf()
    private lateinit var adapter: MenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MenuAdapter(items) {
            val intent = Intent(this, SceneformActivity::class.java)
            intent.putExtra(SceneformActivity.FIELD_URI_STRING, it.gltfUrl)
            startActivity(intent)
        }
        recycler_view.adapter = adapter

        swipe_layout.setOnRefreshListener {
            fetchItems()
        }
    }

    private fun fetchItems() = CoroutineScope(Dispatchers.Main).launch {
        val data = async(Dispatchers.IO) {
            Thread.sleep(2000)
            URL("https://poly.googleapis.com/v1/assets?key=${BuildConfig.PolyAPIKey}").readText()
        }

        val ele: JsonElement = Json.unquoted.parseJson(data.await())
        val assets: JsonArray = ele.jsonObject.getArray("assets")

        for (asset in assets) {
            val submission = Submission(asset.jsonObject)
            items.add(submission)
        }

        adapter.notifyDataSetChanged()
        swipe_layout.isRefreshing = false
    }
}
