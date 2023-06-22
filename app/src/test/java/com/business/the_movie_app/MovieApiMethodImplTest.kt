package com.business.the_movie_app

import com.business.the_movie_app.model.response.Movie
import com.business.the_movie_app.model.response.MovieResponse
import com.business.the_movie_app.remote.MovieApi
import com.business.the_movie_app.remote.MovieApiMethodImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test


class MovieApiMethodImplTest {
    private lateinit var movieApi: MovieApi
    private lateinit var movieApiMethod: MovieApiMethodImpl

    @Before
    fun setup() {
        movieApi = mockk()
        movieApiMethod = MovieApiMethodImpl(movieApi)
    }

    @Test
    fun `getMovies should call MovieApi and return a list of movies`() {
        // Mocked response
        val apiResponse = MovieResponse(listOf(
            Movie(1, "Title 1", "Overview 1", "2023-01-01", "path1.jpg", 4.5),
            Movie(2, "Title 2", "Overview 2", "2023-02-02", "path2.jpg", 3.7)
        ))

        val movieListings:List<Movie> = MovieResponse(listOf(
            Movie(1, "Title 1", "Overview 1", "2023-01-01", "path1.jpg", 4.5),
            Movie(2, "Title 2", "Overview 2", "2023-02-02", "path2.jpg", 3.7)
        )).results
        val apiKey = BuildConfig.MOVIE_API_KEY

        // Mock the behavior of MovieApi
        every { movieApi.getPopularMovies(apiKey) } returns Single.just(apiResponse)

        // Call the method being tested
        val testObserver = movieApiMethod.getMovies(apiKey).test()

        // Verify that MovieApi.getPopularMovies was called with the correct API key
        verify { movieApi.getPopularMovies(apiKey) }

        // Verify the result
        assertThat(testObserver.values().first()).isEqualTo(movieListings)
        testObserver.assertComplete()
    }
}