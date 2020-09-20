package com.example.albumviewer

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class AlbumApplication : Application() {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val TAG: String = AlbumApplication::class.java.simpleName
    private var mRequestQueue: RequestQueue? = null
    private var mImageLoader: ImageLoader? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object{
        var mInstance: AlbumApplication? = null
        @Synchronized
        fun getInstance(): AlbumApplication? {
            return mInstance
        }
    }


    private fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(applicationContext)
        }
        return mRequestQueue
    }

    fun getImageLoader(): ImageLoader?{
        getRequestQueue();
        if(mImageLoader == null){
            mImageLoader = ImageLoader(mRequestQueue, LruBitmapCache())
        }
        return mImageLoader
    }

    fun <T> addToRequestQueue(request: Request<T>, tag: String?) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue()!!.add(request)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = TAG
        getRequestQueue()!!.add(request)
    }

    fun cancelPendingRequest(tag: Any){
        mRequestQueue?.cancelAll(tag);
    }
}