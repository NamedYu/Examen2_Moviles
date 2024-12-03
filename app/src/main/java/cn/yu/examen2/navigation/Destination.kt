package cn.yu.examen2.navigation

import kotlinx.serialization.Serializable

@Serializable
object FlickLista

@Serializable
object FlickSearchList

@Serializable
object FavoriteList

@Serializable
data class FlickDetailData(val peliculaId: Int)
