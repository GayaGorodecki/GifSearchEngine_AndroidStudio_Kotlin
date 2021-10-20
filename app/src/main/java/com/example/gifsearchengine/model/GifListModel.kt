package com.example.gifsearchengine

import com.google.gson.annotations.SerializedName

class GifList (
    @SerializedName("data")
    var gifs : List<Gif>
)

class Gif(
    val url: String,
    val images : Images
)

class Images(
    val fixed_height : FixedHeight
)

class FixedHeight(
    val url : String
)