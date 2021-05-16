package ru.ivadimn.moshitest

import com.squareup.moshi.Moshi

val moviesJson = """
    {"Search":
        [{"Title":"Sher Dil","Year":"2019","imdbID":"tt9129166","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BNzFkNWJhZWYtMmE0Ni00NGUzLWI2ZDktYzFkZDhkNDNkN2RjXkEyXkFqcGdeQXVyODg1MTc3MTM@._V1_SX300.jpg"},
    {"Title":"Sher","Year":"2015","imdbID":"tt5191308","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BMzVjNjE1ZTMtNjJlZi00OGU0LTlkZGYtYmNlOTkwZjg5MzAwXkEyXkFqcGdeQXVyNDY5MTUyNjU@._V1_SX300.jpg"},
    {"Title":"Sher Mountain Killings Mystery","Year":"1990","imdbID":"tt0100596","Type":"movie","Poster":"https://ia.media-imdb.com/images/M/MV5BMTkxNzEzMTQwN15BMl5BanBnXkFtZTcwNDY1MjIyMQ@@._V1_SX300.jpg"},
    {"Title":"Sher-E-Hindustan","Year":"1997","imdbID":"tt0455199","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BNzBmZjRlNzItZDFiYi00NThkLTg0Y2EtMTI0YzIwYjU5ZTAzXkEyXkFqcGdeQXVyMTUxODIyNzk@._V1_SX300.jpg"},
    {"Title":"Sher Dil","Year":"1990","imdbID":"tt0359967","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BNjI0ODFjMTYtYzQ0MS00MTg1LTllYWItMTdjOWJlYmFjNDQyXkEyXkFqcGdeQXVyMTQ1NzgwODk@._V1_SX300.jpg"},
    {"Title":"Sher Qarishanda","Year":"2016","imdbID":"tt8989186","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BZWJjNDIwNTgtNjE4MC00MWM2LTlhYzAtYzEwMjRiYzNlMTllXkEyXkFqcGdeQXVyNDI1MTA1MDA@._V1_SX300.jpg"},
    {"Title":"Pahili Sher, Dusri Savvasher, Navara Pavsher","Year":"2006","imdbID":"tt0984085","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BYWQ3YjEyZDUtYWNhMy00YWMzLWFmNDItNWEwMzI5NmE0Yzc3XkEyXkFqcGdeQXVyMTgwMjgwMjM@._V1_SX300.jpg"},
    {"Title":"Zakhmi Sher","Year":"1984","imdbID":"tt0262933","Type":"movie","Poster":"N/A"},{"Title":"Bhooka Sher","Year":"1984","imdbID":"tt0454407","Type":"movie","Poster":"N/A"},
    {"Title":"Sher Shivaji","Year":"1987","imdbID":"tt0215185","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BZGE1N2ZkZWMtYjRiNy00NTY3LWFhNTktMGE2MDQ2NTcxZGIyXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg"}],
    "totalResults":"64","Response":"True"}
""".trimIndent()



fun main() {
    val moshi = Moshi.Builder()
        .add(MovieAdapter())
        .build()

    val adapter = moshi.adapter<MoviesWrapper>(MoviesWrapper::class.java)

    val movies = adapter.fromJson(moviesJson)
    println(movies)
}