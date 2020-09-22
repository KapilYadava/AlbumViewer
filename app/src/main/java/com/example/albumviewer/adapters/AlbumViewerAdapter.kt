package com.example.albumviewer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.albumviewer.activities.AlbumActivity
import com.example.albumviewer.model.Album
import java.util.*
import kotlin.collections.ArrayList


class AlbumViewerAdapter : RecyclerView.Adapter<AlbumViewerAdapter.MyViewHolder>(), View.OnClickListener, Filterable {

    private var activity: AlbumActivity? = null
    private var albumList: List<Album>? = null
    private var albumListFiltered: List<Album>? = null

    fun setAlbumList(albumList: List<Album>) {
        if (this.albumList == null) {
            this.albumList = albumList
            this.albumListFiltered = albumList
            notifyItemChanged(0, albumListFiltered!!.size)
        } else {
            val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                   return this@AlbumViewerAdapter.albumList!![oldItemPosition].title === albumList[newItemPosition].title
                }

                override fun getOldListSize(): Int {
                    return this@AlbumViewerAdapter.albumList!!.size
                }

                override fun getNewListSize(): Int {
                   return albumList.size
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newList = this@AlbumViewerAdapter.albumList!![oldItemPosition]
                    val oldList = albumList[newItemPosition]
                    return newList.title === oldList.title
                }

            })
            this.albumList = albumList
            albumListFiltered = albumList
            result.dispatchUpdatesTo(this)
        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null

        init {
            textView = itemView.findViewById(android.R.id.text1)
        }
    }

    fun setOnItemViewClickListener(activity: AlbumActivity){
        this.activity = activity
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        itemView.setOnClickListener(this)
        return MyViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return if (albumList != null) {
            albumListFiltered!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView?.text = albumListFiltered?.get(position)!!.title
    }

    override fun onClick(view: View?) {
        activity!!.onItemViewClick(view)
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(view: View?)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                albumListFiltered = if (charString.isEmpty()) {
                    albumList
                } else {
                    val filteredList: MutableList<Album> = ArrayList()
                    for (album in albumList!!) {
                        if (album.title.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))) {
                            filteredList.add(album)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = albumListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                albumListFiltered = (filterResults.values)!! as List<Album>?
                notifyDataSetChanged()
            }
        }
    }
}