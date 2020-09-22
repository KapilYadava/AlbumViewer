package com.example.albumviewer.model

/** data class
 * @constructor(albumId, id, title, url, thumbnailUrl)
 * **/
data class Photo(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)