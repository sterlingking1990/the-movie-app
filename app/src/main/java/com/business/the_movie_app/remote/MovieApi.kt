package com.business.the_movie_app.remote

import com.business.the_movie_app.model.response.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey:String): Single<MovieResponse>
}