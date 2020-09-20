package com.example.albumviewer.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.albumviewer.MyApplication
import com.example.albumviewer.R
import com.example.albumviewer.adapters.PhotoViewerAdapter
import com.example.albumviewer.model.Photo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.lang.reflect.Type

class PhotoActivity : AppCompatActivity() {
    private var gson: Gson? = null
    private var recyclerView: RecyclerView? = null
    private var mInstance: AlbumActivity? = null
    private var mUserId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_activiy)
        gson = GsonBuilder().setDateFormat("M/d/yy hh:mm a").create();
        Log.d("kapil user id", intent.getIntExtra("USER_ID", 0).toString())
        mUserId = intent.getIntExtra("USER_ID", 0);
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //adding a layout manager
        recyclerView!!.layoutManager = GridLayoutManager(mInstance, 2)
        getImages(mUserId)
    }

    private fun getImages(userId: Int) {
        Log.d("kapil", "getImages invoked!")
        val albumUrl = "https://jsonplaceholder.typicode.com/albums/$userId/photos"
        val tagJsonArray = "json_array_request"
        var photos: List<Photo>? = null
        val req = JsonArrayRequest(albumUrl, Response.Listener<JSONArray> {
            Log.d("kapil", it.toString())
            val listType: Type = object : TypeToken<List<Photo?>?>() {}.type
            photos = gson!!.fromJson(it.toString(), listType)
            val adapter = PhotoViewerAdapter(photos!!)
            //mInstance?.let { instance -> adapter.setOnItemViewClickListener(instance) }
            recyclerView!!.adapter = adapter
        }, Response.ErrorListener {
            Log.d("kapil", it.toString())
        })
        MyApplication.getInstance()!!.addToRequestQueue(req, tagJsonArray);
    }
}