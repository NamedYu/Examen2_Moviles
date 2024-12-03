package cn.yu.examen2.data

import android.provider.MediaStore.Audio.Genres
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritesshows")
data class FavoriteShow(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val image: String?
){
    fun toShow():Show{
        val showOut= Show(
            id = this.id,
            name = this.name,
            image = this.image?.let {
                Image(
                    medium = it,
                    original = it
                )
            },
            genres = null,
            url = null,
            type = null,
            language = null,
            status = null,
            runtime = null,
            averageRuntime = null,
            premiered = null,
            ended = null,
            officialSite = null,
            schedule = null,
            rating = null,
            weight = null,
            network = null,
            webChannel = null,
            dvdCountry = null,
            externals = null,
            summary = null,
            updated = null,
            _links = null
        )
        return showOut
    }
}