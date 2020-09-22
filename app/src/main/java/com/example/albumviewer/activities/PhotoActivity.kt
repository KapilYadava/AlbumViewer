package com.example.albumviewer.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.albumviewer.R
import com.example.albumviewer.adapters.PhotoViewerAdapter
import com.example.albumviewer.model.Photo
import com.example.albumviewer.viewmodel.PhotoViewModel
import com.squareup.picasso.Picasso

/**
 * @author kapil kumar
 * class is used for showing photo list
 */

class PhotoActivity : AppCompatActivity(), PhotoViewerAdapter.OnItemViewClickListener {

    private val tag = "PhotoActivity"
    private var recyclerView: RecyclerView? = null
    private var mUserId = 0
    private var mPhotos: List<Photo?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        mUserId = intent.getIntExtra("USER_ID", 0)
        Log.d(tag, "user id : $mUserId")
        recyclerView = findViewById(R.id.recyclerview)
        /**
         * adding a layout manager
         * @see RecyclerView.LayoutManager
         * @see GridLayoutManager
         * **/
        recyclerView!!.layoutManager = GridLayoutManager(this,2)
        /**
         * creating a view model
         * @see PhotoViewModel
         * @see ViewModelProvider.AndroidViewModelFactory.getInstance
         * **/
        val photoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(PhotoViewModel::class.java)
        photoViewModel.init(mUserId)
        photoViewModel.getImages()!!.observe(this, Observer {
            mPhotos = it
            val adapter = PhotoViewerAdapter(it!!)
            this@PhotoActivity.let { instance -> adapter.setOnItemViewClickListener(instance) }
            /**
             * set albumViewerAdapter
             * @see RecyclerView.setAdapter
             **/
            recyclerView!!.adapter = adapter
        })
    }

    /** @View Item click listener for photo
     * @see PhotoViewerAdapter.OnItemViewClickListener
     */
    override fun onItemViewClick(view: View?) {
        val url = mPhotos?.get(recyclerView!!.getChildAdapterPosition(view!!))!!.url
        val builder = Dialog(this)
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        builder.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        val imageView = ImageView(this)
        /** fetch image from  and load to ImageView
         * @param url
         * @param imageview etc..
         * @see ImageView
         * @see Picasso lib
         * **/
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(android.R.drawable.stat_notify_error)
            .into(imageView)
        builder.addContentView(
            imageView, RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        builder.show()
    }
}