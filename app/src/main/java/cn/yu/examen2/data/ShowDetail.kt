package cn.yu.examen2.data

data class ShowDetail(
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
    val _links: Links?,
    val _embedded: EmbeddedCast?
)

data class EmbeddedCast(
    val cast: List<CastMember>
)

data class CastMember(
    val person: Person,
    val character: Character
)

data class Person(
    val id: Int,
    val name: String?,
    val image: Image?,
    val url: String?
)

data class Character(
    val id: Int,
    val name: String?,
    val image: Image?,
    val url: String?
)
