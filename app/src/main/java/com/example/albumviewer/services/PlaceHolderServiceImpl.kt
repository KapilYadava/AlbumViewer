package com.example.albumviewer.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Singleton class for
 * @see PlaceHolderService implementation
 * **/
object PlaceHolderServiceImpl {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private var mPlaceHolderService: PlaceHolderService? = null

    /** method return
     * @return PlaceHolderService instance
     * **/
    fun getService(): PlaceHolderService? {
        if (mPlaceHolderService == null){
            val mRetrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                BASE_URL
            ).build()
            mPlaceHolderService = mRetrofit.create(PlaceHolderService::class.java)
            return mPlaceHolderService
        }
        return mPlaceHolderService
    }
}