package com.business.the_movie_app.domain

import com.business.the_movie_app.core.BaseUseCase
import com.business.the_movie_app.model.response.Movie
import com.business.the_movie_app.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository):BaseUseCase.GetMoviesUseCase {
    override suspend fun getMovies(apiKey:String): Single<List<Movie>> {
        return movieRepository.getMovies(apiKey)
    }

}