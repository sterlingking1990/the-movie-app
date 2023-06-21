package com.business.the_movie_app.core

import com.business.the_movie_app.model.response.Movie
import io.reactivex.Single

interface BaseUseCase {

    interface GetMoviesUseCase {
        suspend fun getMovies(apiKey:String): Single<List<Movie>>
    }
}
