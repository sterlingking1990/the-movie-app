package com.business.the_movie_app.repository

import com.business.the_movie_app.model.response.Movie
import io.reactivex.Single

interface MovieRepository {
    fun getMovies(apiKey:String): Single<List<Movie>>
}
