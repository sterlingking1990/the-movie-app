package com.business.the_movie_app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.business.the_movie_app.domain.MovieUseCase
import com.business.the_movie_app.model.response.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetMoviesViewModel @Inject constructor(
    private val getMovieUseCase: MovieUseCase,
):ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    suspend fun fetchMovies(apiKey:String) {
        Log.d("FetchingMoviews","fetchMoviews")
        getMovieUseCase.getMovies(apiKey)
            .subscribe({ movies ->
                _movies.value = movies
            }, { error ->
                // Handle error
                Log.d("ErrorIs",error.toString())
            })
    }
}