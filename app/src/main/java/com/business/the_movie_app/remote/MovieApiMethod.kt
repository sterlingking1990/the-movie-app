package com.business.the_movie_app.remote

import com.business.the_movie_app.model.response.Movie
import io.reactivex.Single

interface MovieApiMethod {
    fun getMovies(apiKey:String): Single<List<Movie>>
}