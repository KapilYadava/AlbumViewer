package com.example.albumviewer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albumviewer.model.Photo
import com.example.albumviewer.services.PlaceHolderServiceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** PhotoViewModel acts as a helper class for
 * **/
internal class PhotoViewModel : ViewModel() {

    private var mPhotos: MutableLiveData<List<Photo?>?>? = null

    fun init(userId: Int){
        mPhotos = getImages(userId)
    }

    private fun getImages(userId: Int): MutableLiveData<List<Photo?>?>? {
        Log.d("kapil", "getImages invoked!")
        val photos: MutableLiveData<List<Photo?>?>? = MutableLiveData()
        val call: Call<List<Photo?>?> = PlaceHolderServiceImpl.getService()!!.listPhotos(userId)
        call.enqueue(object : Callback<List<Photo?>?> {
            override fun onFailure(call: Call<List<Photo?>?>, t: Throwable) {
                Log.d("TAG", "onFailure invoked!")
            }

            override fun onResponse(
                call: Call<List<Photo?>?>,
                response: Response<List<Photo?>?>
            ) {
                if (response.isSuccessful) {
                    photos!!.value = response.body()
                }
            }
        })
        return photos
    }

    fun getImages(): MutableLiveData<List<Photo?>?>? {
        return mPhotos
    }
}