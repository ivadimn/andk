package ru.ivadimn.moshitest

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

enum class MovieType {
    MOVIE,
    SERIES,
    EPISODE,
    GAME,
    OTHER
}

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "imdbID")
    val id : String,
    @Json(name = "Type")
    val type : MovieType,
    @Json(name = "Title")
    val title : String,
    @Json(name = "Year")
    val year : String,
    @Json(name = "Poster")
    val poster : String
)

@JsonClass(generateAdapter = true)
data class MoviesWrapper(
    @Json(name = "Search")
    val search : List<Movie>,
    @Json(name = "totalResults")
    val totalResults : String,
    @Json(name = "Response")
    val response : String
) {
}

class MovieAdapter {
    @FromJson
    fun fromJson(movieRaw : MovieRaw) : Movie {
        with(movieRaw) {
            return Movie(
                id = id,
                type = MovieType.valueOf(type.uppercase()),
                title = title,
                year = year,
                poster = poster
            )
        }
    }

    @ToJson
    fun toJson(movie : Movie) : MovieRaw {
        with(movie) {
            return MovieRaw(
                id = id,
                type = type.name.lowercase(),
                title = title,
                year = year,
                poster = poster
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class MovieRaw(
        @Json(name = "imdbID")
        val id : String,
        @Json(name = "Type")
        val type : String,
        @Json(name = "Title")
        val title : String,
        @Json(name = "Year")
        val year : String,
        @Json(name = "Poster")
        val poster : String
    )
}