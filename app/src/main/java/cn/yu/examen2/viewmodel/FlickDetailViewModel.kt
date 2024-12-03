package cn.yu.examen2.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import cn.yu.examen2.data.DataProvider
import cn.yu.examen2.data.ShowDetail
import cn.yu.examen2.navigation.FlickDetailData

class FlickDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pelicula = MutableLiveData<ShowDetail?>()
    val pelicula: LiveData<ShowDetail?> = _pelicula

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val peliculaId: Int = savedStateHandle.toRoute<FlickDetailData>().peliculaId

    init {
        getShowDetail(peliculaId)
    }

    fun getShowDetail(showID: Int) {
        DataProvider.getShowDetail(
            showID = showID,
            onSuccess = { showDetail ->
                _pelicula.value = showDetail
            },
            onError = { error ->
                _error.value = "Failed to load data: ${error.message}"
            }
        )
    }
}