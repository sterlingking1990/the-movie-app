package com.business.the_movie_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.business.the_movie_app.domain.MovieUseCase
import com.business.the_movie_app.local.dao.MovieDao
import com.business.the_movie_app.model.response.Movie
import com.business.the_movie_app.presentation.viewmodel.GetMoviesViewModel
import io.mockk.*
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetMoviesViewModelTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieDao: MovieDao
    private lateinit var getMovieUseCase: MovieUseCase
    private lateinit var observer: Observer<List<Movie>>
    private lateinit var viewModel: GetMoviesViewModel


    @Before
    fun setup() {
        movieDao = mockk()
        getMovieUseCase = mockk()
        viewModel = GetMoviesViewModel(getMovieUseCase, movieDao)
        observer = mockk(relaxed = true)
        viewModel.movies.observeForever(observer)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun teardown() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun fetchMovies_shouldLoadFromDatabaseWhenCachedMoviesExist() {
        val cachedMovies = listOf(Movie(1,"Title 1","Something nice 1","2023-02-09","moviepath1.jpg",4.7), Movie(2,"Title 2","Something nice2","2023-04-09","moviepath2.jpg",3.7))

        coEvery { movieDao.getMovies() } returns Single.just(cachedMovies)

        viewModel.fetchMovies(BuildConfig.MOVIE_API_KEY)

        coVerify(exactly = 0) { getMovieUseCase.getMovies(any()) }
        coVerify { observer.onChanged(cachedMovies) }
    }

    @Test
    fun fetchMovies_shouldFetchFromApiWhenCachedMoviesEmpty() {

        val apiMovies = listOf(Movie(3,"Title 3","Something nice","2023-11-09","moviepath.jpg",9.7), Movie(4,"Title 4","Something nice","2023-11-09","moviepath4.jpg",5.7))

        coEvery { movieDao.getMovies() } returns Single.just(emptyList())
        coEvery { getMovieUseCase.getMovies(anyString()) } returns Single.just(apiMovies)

        viewModel.fetchMovies(anyString())
        coEvery {
            getMovieUseCase.getMovies(eq(anyString()))
        } returns Single.just(apiMovies)
        coEvery {
            movieDao.insertMovies(apiMovies)
        } just Runs

        viewModel.movies.observeForever(observer)
    }
}