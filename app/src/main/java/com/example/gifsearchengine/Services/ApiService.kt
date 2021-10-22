package com.example.gifsearchengine

import com.example.gifsearchengine.model.CategoriesList
import com.example.gifsearchengine.view.CategoriesFragment
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("/v1/gifs/trending?api_key=Zz7XnA0RZzJJetQAQv1e2c7ErivA9F5u")
    fun getTrendingGifs(): Call<GifList>

    @GET("/v1/gifs/search?api_key=Zz7XnA0RZzJJetQAQv1e2c7ErivA9F5u")
    fun getGifsBySearch(@Query("q") search: String): Call<GifList>

    @GET("/v1/gifs/categories?api_key=Zz7XnA0RZzJJetQAQv1e2c7ErivA9F5u")
    fun getCategories(): Call<CategoriesList>
}

object ApiService {
    private const val baseUrl = "https://api.giphy.com/v1/gifs/"
    private val okHttp = OkHttpClient.Builder()

    private val builder = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}