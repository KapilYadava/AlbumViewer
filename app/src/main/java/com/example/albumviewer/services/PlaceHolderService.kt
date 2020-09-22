package com.example.albumviewer.services

import com.example.albumviewer.model.Album
import com.example.albumviewer.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/** API client interface methods
 * @see listAlbums
 * @see listPhotos
 * **/
interface PlaceHolderService {
    @GET("albums")
    fun listAlbums(): Call<List<Album?>?>

    @GET("albums/{userId}/photos")
    fun listPhotos(@Path("userId") userId: Int): Call<List<Photo?>?>
}

