package com.example.albumviewer.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.albumviewer.R
import com.example.albumviewer.adapters.AlbumViewerAdapter
import com.example.albumviewer.model.Album
import com.example.albumviewer.viewmodel.AlbumViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author kapil kumar
 * class is used for showing photo list
 */

class AlbumActivity : AppCompatActivity(), AlbumViewerAdapter.OnItemViewClickListener {

    private val tag = "AlbumActivity"
    private var gson: Gson? = null
    private var recyclerView: RecyclerView? = null
    private var mAlbums: List<Album?>? = null
    private var adapter: AlbumViewerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        gson = GsonBuilder().setDateFormat("M/d/yy hh:mm a").create()
        recyclerView = findViewById(R.id.recyclerview)
        /**
         * adding a layout manager
         * @see RecyclerView.LayoutManager
         * @see LinearLayoutManager
         * **/
        recyclerView!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        /**
         * creating a view model
         * @see AlbumViewModel
         * @see ViewModelProvider.AndroidViewModelFactory.getInstance
         * **/
        val albumViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(AlbumViewModel::class.java)
        albumViewModel.getAlbums()!!.observe(this, Observer {
            mAlbums = it
            adapter = AlbumViewerAdapter()
            adapter!!.setAlbumList(it as List<Album>)
            this@AlbumActivity.let { instance -> adapter!!.setOnItemViewClickListener(instance) }
            recyclerView!!.adapter = adapter
        })
    }

    /** @View Item click listener for album
     * @see AlbumViewerAdapter.OnItemViewClickListener
     */
    override fun onItemViewClick(view: View?) {
        Log.d(tag, "onItemViewClick")
        val intent = Intent(this, PhotoActivity::class.java).apply {
            putExtra(
                "USER_ID", mAlbums?.get(recyclerView!!.getChildAdapterPosition(view!!))!!.userId
            )
        }
        startActivity(intent)
    }

    /** create SearchView with
     * @see SearchManager
     * **/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: android.widget.SearchView = menu!!.findItem(R.id.app_bar_search).actionView as android.widget.SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.v(tag, query +"")
                adapter!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.v(tag, newText +"")
                adapter!!.filter.filter(newText)
                return false
            }
        })

        return true
    }
}
