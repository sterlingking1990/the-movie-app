package com.business.the_movie_app.remote

import com.business.the_movie_app.model.response.Movie
import io.reactivex.Single
import javax.inject.Inject

class MovieApiMethodImpl @Inject constructor(private val movieApi: MovieApi) : MovieApiMethod {
    override fun getMovies(apiKey:String): Single<List<Movie>> {
        return movieApi.getPopularMovies(apiKey)
            .map { response -> response.results }
    }
}