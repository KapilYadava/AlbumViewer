package com.example.albumviewer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.albumviewer.activities.AlbumActivity
import com.example.albumviewer.model.Album

class AlbumViewerAdapter(private val list: List<Album>) :
    RecyclerView.Adapter<AlbumViewerAdapter.MyViewHolder>(), View.OnClickListener {

    private var activity: AlbumActivity? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null

        init {
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    fun setOnItemViewClickListener(activity: AlbumActivity){
        this.activity = activity
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        itemView.setOnClickListener(this)
        return MyViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView?.text = list[position].title
    }

    override fun onClick(view: View?) {
        activity!!.onItemViewClick(view);
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(view: View?);
    }
}