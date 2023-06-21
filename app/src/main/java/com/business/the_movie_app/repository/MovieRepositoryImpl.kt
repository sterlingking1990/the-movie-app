package com.business.the_movie_app.repository

import android.util.Log
import com.business.the_movie_app.local.dao.MovieDao
import com.business.the_movie_app.model.response.Movie
import com.business.the_movie_app.remote.MovieApiMethod
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val movieApiMethod: MovieApiMethod,
) : MovieRepository {
    override fun getMovies(apiKey:String): Single<List<Movie>> {
        return movieApiMethod.getMovies(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}