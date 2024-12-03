package cn.yu.examen2.data

import retrofit2.Callback

data class Show(
    val id: Int,
    val url: String?,
    val name: String?,
    val type: String?,
    val language: String?,
    val genres: List<String>?,
    val status: String?,
    val runtime: Int?,
    val averageRuntime: Int?,
    val premiered: String?,
    val ended: String?,
    val officialSite: String?,
    val schedule: Schedule?,
    val rating: Rating?,
    val weight: Int?,
    val network: Network?,
    val webChannel: WebChannel?,
    val dvdCountry: Country?,
    val externals: Externals?,
    val image: Image?,
    val summary: String?,
    val updated: Long?,
    val _links: Links?
){
    fun toDetail():ShowDetail{
        val showDetail = ShowDetail(
            id = this.id,
            url = this.url,
            name = this.name,
            type = this.type,
            language = this.language,
            genres = this.genres,
            status = this.status,
            runtime = this.runtime,
            averageRuntime = this.averageRuntime,
            premiered = this.premiered,
            ended = this.ended,
            officialSite = this.officialSite,
            schedule = this.schedule,
            rating = this.rating,
            weight = this.weight,
            network = this.network,
            webChannel = this.webChannel,
            dvdCountry = this.dvdCountry,
            externals = this.externals,
            image = this.image,
            summary = this.summary,
            updated = this.updated,
            _links = this._links,
            _embedded = null
        )
        return showDetail
    }
}

data class Schedule(
    val time: String,
    val days: List<String>
)

data class Network(
    val id: Int,
    val name: String,
    val country: Country?,
    val officialSite: String?
)

data class WebChannel(
    val id: Int,
    val name: String,
    val country: Country?
)

data class Country(
    val name: String,
    val code: String,
    val timezone: String
)

data class Externals(
    val tvrage: Int?,
    val thetvdb: Int?,
    val imdb: String?
)

data class Image(
    val medium: String,
    val original: String
)

data class Links(
    val self: Link,
    val previousepisode: Link?
)

data class Link(
    val href: String
)

data class Rating(
    val average: Double?
)
