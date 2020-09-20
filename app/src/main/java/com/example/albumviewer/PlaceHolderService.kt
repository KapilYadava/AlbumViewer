package com.example.albumviewer

import com.example.albumviewer.model.Album
import retrofit2.Call
import retrofit2.http.GET

interface PlaceHolderService {
    @GET("albums")
    fun listAlbums(): Call<List<Album?>?>?
}

