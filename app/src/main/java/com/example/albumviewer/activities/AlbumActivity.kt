package com.example.albumviewer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.albumviewer.MyApplication
import com.example.albumviewer.R
import com.example.albumviewer.adapters.AlbumViewerAdapter
import com.example.albumviewer.model.Album
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.lang.reflect.Type

class AlbumActivity : AppCompatActivity(), RecyclerView.OnItemTouchListener, AlbumViewerAdapter.OnItemViewClickListener {

    private var gson: Gson? = null
    private var recyclerView: RecyclerView? = null
    private var mInstance: AlbumActivity? = null
    private var albums: List<Album>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mInstance = this
        gson = GsonBuilder().setDateFormat("M/d/yy hh:mm a").create();
        //getting recyclerview from xml
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //adding a layout manager
        recyclerView!!.layoutManager = LinearLayoutManager(mInstance, RecyclerView.VERTICAL, false)
        getAlbums()

        //recyclerView!!.addOnItemTouchListener(this)
    }

    private fun getAlbums() {
        Log.d("kapil", "getAlbums invoked!")
        val albumUrl = "https://jsonplaceholder.typicode.com/albums"
        val tagJsonArray = "json_array_request"
        val req = JsonArrayRequest(albumUrl, Response.Listener<JSONArray> {
            Log.d("kapil", it.toString())
            val listType: Type = object : TypeToken<List<Album?>?>() {}.type
            albums = gson!!.fromJson(it.toString(), listType)
            val adapter = AlbumViewerAdapter(albums!!)
            mInstance?.let { instance -> adapter.setOnItemViewClickListener(instance) }
            recyclerView!!.adapter = adapter
        }, Response.ErrorListener {
            Log.d("kapil", it.toString())
        })
        MyApplication.getInstance()!!.addToRequestQueue(req, tagJsonArray);
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("kapil", "onItemViewClick")
//                val intent = Intent(this, PhotoActivity::class.java).apply {
//                    putExtra("USER_ID",rv.getCh )
//                }
                startActivity(intent)
            }
        }
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return true
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    override fun onItemViewClick(view: View?) {
        Log.d("kapil", "onItemViewClick")
                val intent = Intent(mInstance, PhotoActivity::class.java).apply {
                    putExtra("USER_ID",
                        albums?.get(recyclerView!!.getChildAdapterPosition(view!!))!!.userId
                    )
                }
        startActivity(intent)
    }

}

