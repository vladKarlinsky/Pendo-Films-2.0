package com.example.pendofilms.network

import com.example.pendofilms.model.GetResponseMovies
import com.example.pendofilms.model.GetSearchSuggestions
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.themoviedb.org"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {

    @GET("3/discover/movie?" +
            "api_key=4a1e384e2c307db119f16abdbd6deeb8" +
            "&language=en-US" +
            "&sort_by=popularity.desc" +
            "&include_adult=false" +
            "&include_video=false" +
            "&page=1" +
            "&with_watch_monetization_types=flatrate")
    fun getDefaultMovies(): Call<GetResponseMovies>

    @GET("3/search/movie?" +
            "api_key=4a1e384e2c307db119f16abdbd6deeb8")
    fun getMoviesSearch(@Query("query") query: String): Call<GetResponseMovies>

    @GET("3/search/keyword?" +
            "api_key=4a1e384e2c307db119f16abdbd6deeb8")
    fun getSuggestions(@Query("query") query: String): Call<GetSearchSuggestions>
}

object MovieApi {
    val retrofitService : MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java) }
}