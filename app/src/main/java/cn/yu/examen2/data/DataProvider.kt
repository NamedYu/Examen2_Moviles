package cn.yu.examen2.data

import android.content.Context
import com.squareup.moshi.Moshi
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataProvider{
    var shows = listOf<Show>()
    private val tvMazeApiService:TvMazeApiService
        = ApiConnector.instance.create(TvMazeApiService::class.java)
    fun loadShows(onError:(Throwable) -> Unit,onSuccess: (List<Show>) -> Unit){
        val _shows = mutableListOf<Show>()
        tvMazeApiService.getShows().enqueue(object : retrofit2.Callback<List<Show>>{
            override fun onResponse(call: Call<List<Show>>, response: Response<List<Show>>) {
                if (response.isSuccessful){
                    response.body().let {
                        it?.let { it1 -> _shows.addAll(it1.take(50)) }
                    }
                }

                shows = _shows
                onSuccess(shows)
            }

            override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun searchShows(query: String, onError: (Throwable) -> Unit, onSuccess: (List<Show>) -> Unit) {
        tvMazeApiService.searchShows(query).enqueue(object : retrofit2.Callback<List<SearchResult>> {
            override fun onResponse(call: Call<List<SearchResult>>, response: Response<List<SearchResult>>) {
                if (response.isSuccessful) {
                    val searchResults = response.body()?.map { it.show } ?: emptyList()
                    onSuccess(searchResults)
                } else {
                    onError(Exception("Failed with code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<SearchResult>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getShowDetail(showID:Int, onError: (Throwable) -> Unit, onSuccess: (ShowDetail) -> Unit){
        tvMazeApiService.getShowDetail(showID).enqueue(object : Callback<ShowDetail>{
            override fun onResponse(call: Call<ShowDetail>, response: Response<ShowDetail>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        onSuccess(it)
                    }
                }else {
                    onError(Exception("Failed with code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ShowDetail>, t: Throwable) {
                onError(t)
            }
        })
    }



}

