package cn.yu.examen2.data

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val showsRepository: ShowsRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val BASE_URL = "https://api.tvmaze.com/"
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()


    private val retrofitService: TvMazeApiService by lazy {
        retrofit.create(TvMazeApiService::class.java)
    }

    private val database: AppDatabase by lazy {
        AppDatabase.getInstance(context)
    }

    override val showsRepository: ShowsRepository by lazy {
        ShowsRepository(retrofitService, database.showDao())
    }
}