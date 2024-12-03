package cn.yu.examen2.data

import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class ShowsRepository(
    private val showsApiService:TvMazeApiService,
    private val showDao: ShowDao
) {
    suspend fun getShows(): List<Show> = showsApiService.getAllShows()
    suspend fun getShowById(id: Int): ShowDetail = showsApiService.getById(id)
    suspend fun getShowsByName(name: String): List<Show> {
        val response = showsApiService.searchByName(name)
        return response.map { it.show }
    }
    suspend fun addFavoriteShow(favoriteShow: FavoriteShow) {
        showDao.insertFavoriteShow(favoriteShow)
    }

    suspend fun getAllFavoriteShows(): List<FavoriteShow> {
        return showDao.getAll()
    }

    suspend fun removeFavoriteShow(favoriteShow: FavoriteShow) {
        showDao.deleteFavoriteShow(favoriteShow)
    }

    suspend fun isFavoriteShow(id: Int): Boolean {
        return showDao.getFavoriteShowById(id) != null
    }
}