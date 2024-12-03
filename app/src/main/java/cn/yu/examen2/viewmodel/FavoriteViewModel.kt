package cn.yu.examen2.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cn.yu.examen2.data.FavoriteShow
import cn.yu.examen2.data.Show
import cn.yu.examen2.data.ShowDetail
import cn.yu.examen2.data.ShowsRepository
import cn.yu.examen2.ui.ShowsApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class UIState(
    val shows: List<Show> = emptyList(),
    val selectedShow: ShowDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val favoritesShow: List<FavoriteShow> = emptyList(),
)

class FavoriteViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {
    val uiState = mutableStateOf(UIState())


    //database
    fun getAllFavoriteShows() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allFavorites = showsRepository.getAllFavoriteShows()
                uiState.value = uiState.value.copy(favoritesShow = allFavorites)
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(error = e.message)
            }
        }
    }

    fun addFavoriteShow(favoriteShow: FavoriteShow) {
        viewModelScope.launch((Dispatchers.IO)) {
            showsRepository.addFavoriteShow(favoriteShow)
            val allFavorites = showsRepository.getAllFavoriteShows()
            Log.d("TAG", "Todos los favoritos: $allFavorites")
        }
    }

    fun deleteFavoriteShow(favoriteShow: FavoriteShow) {
        viewModelScope.launch(Dispatchers.IO) {
            showsRepository.removeFavoriteShow(favoriteShow)
        }
    }

    fun isFavoriteShow(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch((Dispatchers.IO)) {
            val result = showsRepository.isFavoriteShow(id)
            onResult(result)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (
                        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                                ShowsApplication)
                val showsRepository = application.container.showsRepository
                FavoriteViewModel(showsRepository = showsRepository)
            }
        }
    }
}
