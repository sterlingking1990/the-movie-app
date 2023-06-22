package com.business.the_movie_app.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.business.the_movie_app.domain.MovieUseCase
import com.business.the_movie_app.local.dao.MovieDao
import com.business.the_movie_app.model.response.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GetMoviesViewModel @Inject constructor(
    private val getMovieUseCase: MovieUseCase,
    private val movieDao: MovieDao
):ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies


    @SuppressLint("CheckResult")
    fun fetchMovies(apiKey: String) {

        movieDao.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cachedMovies ->
                if (cachedMovies.isNotEmpty()) {
                    _movies.value = cachedMovies
                } else {
                    CoroutineScope(Dispatchers.IO).launch {   getMoviesFromApi(apiKey)}
                }
            }, { error ->
                // Handle error
            })
    }

    @SuppressLint("CheckResult")
    private suspend fun getMoviesFromApi(apiKey: String) {
        getMovieUseCase.getMovies(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                _movies.value = movies
                saveMoviesToDatabase(movies)
            }, { error ->
                // Handle error
            })
    }

    private fun saveMoviesToDatabase(movies: List<Movie>) {
        viewModelScope.launch {
            movieDao.insertMovies(movies)
        }
    }
}