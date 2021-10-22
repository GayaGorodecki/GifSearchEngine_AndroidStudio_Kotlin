package com.example.gifsearchengine.model

import com.google.gson.annotations.SerializedName

class CategoriesList (
    @SerializedName("data")
    var categories : List<Category>
)

class Category(
    val name: String
)