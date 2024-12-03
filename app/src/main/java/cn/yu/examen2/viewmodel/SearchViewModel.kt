package cn.yu.examen2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cn.yu.examen2.data.DataProvider

class SearchViewModel: ViewModel() {
    val flickList = mutableStateOf( DataProvider.shows)
}