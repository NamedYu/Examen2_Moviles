package cn.yu.examen2.data

import android.content.Context
import cn.yu.examen2.navigation.FlickDetailData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object ApiConnector{
    private const val BASE_URL = "https://api.tvmaze.com/"

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Configurar url basico
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // convertidor json
            .build()
    }
}

interface TvMazeApiService {

    // get 50 tv shows
    @GET("shows")
    fun getShows(): Call<List<Show>>

    @GET("shows")
    fun getAllShows(): List<Show>

    // search tv shows
    @GET("search/shows")
    fun searchShows(@Query("q") query: String): Call<List<SearchResult>>

    @GET("search/shows")
    fun searchByName(@Query("q") query: String): List<SearchResult>

    // get tv shows detail
    @GET("shows/{id}")
    fun getShowDetail(@Path("id") showID: Int, @Query("embed") embed: String = "cast"): Call<ShowDetail>

    @GET("shows/{id}")
    fun getById(@Path("id") showID: Int, @Query("embed") embed: String = "cast"): ShowDetail
}