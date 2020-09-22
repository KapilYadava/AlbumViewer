package com.example.albumviewer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.albumviewer.R
import com.example.albumviewer.activities.PhotoActivity
import com.example.albumviewer.model.Photo
import com.squareup.picasso.Picasso

class PhotoViewerAdapter(private val list: List<Photo?>?) :
    RecyclerView.Adapter<PhotoViewerAdapter.MyViewHolder>(), View.OnClickListener {

    private var activity: PhotoActivity? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView? = null
        var image: ImageView? = null
        init {
            title = itemView.findViewById(R.id.title)
            image = itemView.findViewById(R.id.image)
        }
    }

    fun setOnItemViewClickListener(activity: PhotoActivity) {
        this.activity = activity
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_item_view, parent, false)
        itemView.setOnClickListener(this)
        return MyViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title?.text = list?.get(position)!!.title
        Picasso.get()
            .load(list[position]!!.thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.image)
    }

    override fun onClick(view: View?) {
        activity!!.onItemViewClick(view)
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(view: View?)
    }
}