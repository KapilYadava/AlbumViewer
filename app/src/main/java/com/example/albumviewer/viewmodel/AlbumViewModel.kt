package com.example.albumviewer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albumviewer.model.Album
import com.example.albumviewer.services.PlaceHolderServiceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** AlbumViewModel acts as a helper class
 * **/
internal class AlbumViewModel: ViewModel() {

    private val tag = "AlbumViewModel"
    var mAlbums: MutableLiveData<List<Album?>?>? = fetchAlbums()

    private fun fetchAlbums(): MutableLiveData<List<Album?>?>? {
        Log.d("kapil", "getAlbums invoked!")
        val albums: MutableLiveData<List<Album?>?>? = MutableLiveData()
        val call: Call<List<Album?>?> = PlaceHolderServiceImpl.getService()!!.listAlbums()
        call.enqueue(object : Callback<List<Album?>?> {
            override fun onFailure(call: Call<List<Album?>?>, t: Throwable) {
                Log.d(tag, "onFailure invoked!")
            }

            override fun onResponse(call: Call<List<Album?>?>, response: Response<List<Album?>?>) {
                if (response.isSuccessful) {
                    albums!!.value = response.body()
                }
            }
        })
        return albums
    }

    fun getAlbums(): MutableLiveData<List<Album?>?>?{
        return mAlbums
    }

}
